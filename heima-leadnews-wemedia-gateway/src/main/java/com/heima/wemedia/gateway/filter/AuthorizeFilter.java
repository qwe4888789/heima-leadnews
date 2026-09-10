package com.heima.wemedia.gateway.filter;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.heima.model.admin.pojos.AdUser;
import com.heima.model.wemedia.pojos.WmUser;
import com.heima.utils.common.JwtUtils;
import com.heima.utils.common.Payload;
import com.heima.utils.common.RsaUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.PublicKey;

/**
 * 管理员鉴权
 */
@Component  //生效
public class AuthorizeFilter implements GlobalFilter, Ordered {
    @Value("${leadnews.jwt.publicKeyPath}")
    private String publicKeyPath;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //1.获取请求和响应
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        //2.对登录请求直接放行
        //1）取出当前请求URI
        String uri = request.getURI().getPath(); // /login/in
        if(uri.contains("/login/in")){
            //2）放行
            return chain.filter(exchange);
        }

        //3.从请求头中取出token
        String token = request.getHeaders().getFirst("token");
        if(StringUtils.isEmpty(token)){
            //拒绝  返回401状态码
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //终止请求
            return response.setComplete();
        }

        //4.使用Jwt工具+RSA公钥校验token合法性
        try {
            //1）读取公钥文件
            PublicKey publicKey = RsaUtils.getPublicKey(publicKeyPath);
            //2）解析token
            Payload<WmUser> payload = JwtUtils.getInfoFromToken(token, publicKey, WmUser.class);

            //5.从token中取出用户信息
            WmUser wmUser = payload.getInfo();

            //6.把用户信息存入request的请求头（传递给后续的微服务使用）
            request.mutate().header("userId",wmUser.getId()+"");
        } catch (Exception e) {
            //拒绝  返回401状态码
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //终止请求
            return response.setComplete();
        }

        //7.放行请求
        return chain.filter(exchange);
    }

    /**
     * 返回数值，数值越大，优先级越小
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}

package com.heima.article.filter;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.heima.model.user.pojos.ApUser;
import com.heima.utils.common.ThreadLocalUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 统一获取App用户登录信息
 */
@Component
@Order(1)  // 定义过滤器执行顺序，数值越小，优先级越大
@WebFilter(filterName = "apUserTokenFilter",urlPatterns = "/*")
public class ApUserTokenFilter extends GenericFilter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //1.获取request和response
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        //2.取出userId的值
        String userId = request.getHeader("userId");

        if(StringUtils.isNotEmpty(userId) && !userId.equals("0")){  //0 代表设备
            //3.判断是否存在，如果存在，封装对象，存入ThreadLocal对象
            ApUser apUser = new ApUser();
            apUser.setId(Integer.valueOf(userId));
            ThreadLocalUtils.set(apUser);
        }

        //3.放行请求
        filterChain.doFilter(request,response);
    }
}

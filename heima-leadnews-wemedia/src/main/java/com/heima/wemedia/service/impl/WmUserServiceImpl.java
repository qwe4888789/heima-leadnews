package com.heima.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.common.dtos.Result;
import com.heima.common.exception.AppHttpCodeEnum;
import com.heima.common.exception.LeadNewsException;
import com.heima.model.admin.pojos.AdUser;
import com.heima.model.wemedia.pojos.WmUser;
import com.heima.utils.common.BCrypt;
import com.heima.utils.common.JwtUtils;
import com.heima.utils.common.RsaUtils;
import com.heima.wemedia.mapper.WmUserMapper;
import com.heima.wemedia.service.WmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 自媒体用户信息表 服务实现类
 * </p>
 *
 * @author heima
 * @since 2021-07-22
 */
@Service
@Transactional
public class WmUserServiceImpl extends ServiceImpl<WmUserMapper, WmUser> implements WmUserService {
    @Autowired
    private WmUserMapper wmUserMapper;
    @Value("${leadnews.jwt.privateKeyPath}")
    private String privateKeyPath;
    @Value("${leadnews.jwt.expire}")
    private Integer expire;

    @Override
    public Result<WmUser> findUser(Integer apUserId) {
        try {
            QueryWrapper<WmUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("ap_user_id",apUserId);
            WmUser wmUser = getOne(queryWrapper);
            return Result.ok(wmUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new LeadNewsException(AppHttpCodeEnum.SERVER_ERROR);
        }
    }

    @Override
    public Result<Map<String, Object>> login(WmUser user) {
        //1.判断用户名密码是否为空
        if(user==null){
            throw new LeadNewsException(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        if(StringUtils.isNotEmpty(user.getName()) && StringUtils.isNotEmpty(user.getPassword())){
            //2.校验用户名是否存在
            QueryWrapper<WmUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name",user.getName());
            WmUser loginUser = wmUserMapper.selectOne(queryWrapper);

            if(loginUser==null){
                throw new LeadNewsException(AppHttpCodeEnum.AP_USER_DATA_NOT_EXIST);
            }

            //3.判断密码是否正确
            if(!BCrypt.checkpw(user.getPassword(),loginUser.getPassword())){
                throw new LeadNewsException(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
            }

            //4.使用JWT+RSA私钥生成token
            try {
                //1）读取私钥
                PrivateKey privateKey = RsaUtils.getPrivateKey(privateKeyPath);
                String token = JwtUtils.generateTokenExpireInMinutes(loginUser, privateKey, expire);

                //5.封装数据返回
                Map<String,Object> map = new HashMap<>();
                map.put("token",token);
                //密码去掉
                loginUser.setPassword(null);
                map.put("user",loginUser);

                return Result.ok(map);
            } catch (Exception e) {
                e.printStackTrace();
                throw new LeadNewsException(AppHttpCodeEnum.TOKEN_INVALID);
            }
        }
        return Result.error();
    }
}

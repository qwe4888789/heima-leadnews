package com.heima.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.common.dtos.Result;
import com.heima.common.exception.AppHttpCodeEnum;
import com.heima.common.exception.LeadNewsException;
import com.heima.model.user.dtos.LoginDto;
import com.heima.model.user.pojos.ApUser;
import com.heima.user.mapper.ApUserMapper;
import com.heima.user.service.ApUserService;
import com.heima.utils.common.BCrypt;
import com.heima.utils.common.JwtUtils;
import com.heima.utils.common.RsaUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * APP用户信息表 服务实现类
 * </p>
 *
 * @author heima
 * @since 2021-07-22
 */
@Service
@Transactional
public class ApUserServiceImpl extends ServiceImpl<ApUserMapper, ApUser> implements ApUserService {
    @Value("${leadnews.jwt.privateKeyPath}")
    private String privateKeyPath;
    @Value("${leadnews.jwt.expire}")
    private Integer expire;

    @Override
    public Result<Map<String, Object>> login(LoginDto dto) {
        //1.数据参数校验
        if(dto.getEquipmentId()==null && StringUtils.isEmpty(dto.getPhone())
             && StringUtils.isEmpty(dto.getPassword())){
            throw new LeadNewsException(AppHttpCodeEnum.PARAM_INVALID);
        }

        if(StringUtils.isNotEmpty(dto.getPhone())
                && StringUtils.isNotEmpty(dto.getPassword())){
            //2.手机号登录

            //判断手机号是否存在
            QueryWrapper<ApUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("phone",dto.getPhone());
            ApUser loginUser = getOne(queryWrapper);

            if(loginUser==null){
                throw new LeadNewsException(AppHttpCodeEnum.AP_USER_DATA_NOT_EXIST);
            }

            //判断密码是否正确
            if(!BCrypt.checkpw(dto.getPassword(),loginUser.getPassword())){
                throw new LeadNewsException(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
            }

            //生成token
            try {
                PrivateKey privateKey = RsaUtils.getPrivateKey(privateKeyPath);
                String token = JwtUtils.generateTokenExpireInMinutes(loginUser, privateKey, expire);

                Map<String,Object> map = new HashMap<>();
                map.put("token",token);
                loginUser.setPassword(null);
                map.put("user",loginUser);

                return Result.ok(map);
            } catch (Exception e) {
                e.printStackTrace();
                throw new LeadNewsException(AppHttpCodeEnum.TOKEN_INVALID);
            }
        }else{
            //3.设备登录
            //生成token
            try {
                PrivateKey privateKey = RsaUtils.getPrivateKey(privateKeyPath);
                ApUser apUser = new ApUser();
                apUser.setId(0);
                String token = JwtUtils.generateTokenExpireInMinutes(apUser, privateKey, expire);

                Map<String,Object> map = new HashMap<>();
                map.put("token",token);

                return Result.ok(map);
            } catch (Exception e) {
                e.printStackTrace();
                throw new LeadNewsException(AppHttpCodeEnum.TOKEN_INVALID);
            }
        }
    }
}

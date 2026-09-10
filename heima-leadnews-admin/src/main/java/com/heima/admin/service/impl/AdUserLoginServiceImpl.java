package com.heima.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.admin.mapper.AdUserLoginMapper;
import com.heima.admin.service.AdUserLoginService;
import com.heima.model.admin.pojos.AdUserLogin;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员登录行为信息表 服务实现类
 * </p>
 *
 * @author heima
 * @since 2021-07-21
 */
@Service
public class AdUserLoginServiceImpl extends ServiceImpl<AdUserLoginMapper, AdUserLogin> implements AdUserLoginService {

}

package com.heima.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.model.user.pojos.ApUserIdentity;
import com.heima.user.mapper.ApUserIdentityMapper;
import com.heima.user.service.ApUserIdentityService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * APP身份认证信息表 服务实现类
 * </p>
 *
 * @author heima
 * @since 2021-07-22
 */
@Service
public class ApUserIdentityServiceImpl extends ServiceImpl<ApUserIdentityMapper, ApUserIdentity> implements ApUserIdentityService {

}

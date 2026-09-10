package com.heima.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.model.user.pojos.ApUserInfo;
import com.heima.user.mapper.ApUserInfoMapper;
import com.heima.user.service.ApUserInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * APP用户信息表 服务实现类
 * </p>
 *
 * @author heima
 * @since 2021-07-22
 */
@Service
public class ApUserInfoServiceImpl extends ServiceImpl<ApUserInfoMapper, ApUserInfo> implements ApUserInfoService {

}

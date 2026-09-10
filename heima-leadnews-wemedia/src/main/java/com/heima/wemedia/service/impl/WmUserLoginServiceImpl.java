package com.heima.wemedia.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.model.wemedia.pojos.WmUserLogin;
import com.heima.wemedia.mapper.WmUserLoginMapper;
import com.heima.wemedia.service.WmUserLoginService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 自媒体用户登录行为信息表 服务实现类
 * </p>
 *
 * @author heima
 * @since 2021-07-22
 */
@Service
public class WmUserLoginServiceImpl extends ServiceImpl<WmUserLoginMapper, WmUserLogin> implements WmUserLoginService {

}

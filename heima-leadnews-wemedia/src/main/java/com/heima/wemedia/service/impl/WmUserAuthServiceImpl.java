package com.heima.wemedia.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.model.wemedia.pojos.WmUserAuth;
import com.heima.wemedia.mapper.WmUserAuthMapper;
import com.heima.wemedia.service.WmUserAuthService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 自媒体子账号权限信息表 服务实现类
 * </p>
 *
 * @author heima
 * @since 2021-07-22
 */
@Service
public class WmUserAuthServiceImpl extends ServiceImpl<WmUserAuthMapper, WmUserAuth> implements WmUserAuthService {

}

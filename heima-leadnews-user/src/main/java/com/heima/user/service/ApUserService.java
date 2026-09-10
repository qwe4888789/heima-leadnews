package com.heima.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.common.dtos.Result;
import com.heima.model.user.dtos.LoginDto;
import com.heima.model.user.pojos.ApUser;

import java.util.Map;

/**
 * <p>
 * APP用户信息表 服务类
 * </p>
 *
 * @author heima
 * @since 2021-07-22
 */
public interface ApUserService extends IService<ApUser> {

    Result<Map<String, Object>> login(LoginDto dto);
}

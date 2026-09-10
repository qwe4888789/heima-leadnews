package com.heima.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.common.dtos.Result;
import com.heima.model.admin.pojos.AdUser;

import java.util.Map;

/**
 * <p>
 * 管理员用户信息表 服务类
 * </p>
 *
 * @author heima
 * @since 2021-07-21
 */
public interface AdUserService extends IService<AdUser> {

    Result<Map<String, Object>> login(AdUser user);
}

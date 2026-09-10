package com.heima.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.common.dtos.Result;
import com.heima.model.wemedia.pojos.WmUser;

import java.util.Map;

/**
 * <p>
 * 自媒体用户信息表 服务类
 * </p>
 *
 * @author heima
 * @since 2021-07-22
 */
public interface WmUserService extends IService<WmUser> {

    Result<WmUser> findUser(Integer apUserId);

    Result<Map<String, Object>> login(WmUser user);
}

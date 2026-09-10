package com.heima.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.common.dtos.Result;
import com.heima.model.user.dtos.AuthDTO;
import com.heima.model.user.pojos.ApUserRealname;

/**
 * <p>
 * APP实名认证信息表 服务类
 * </p>
 *
 * @author heima
 * @since 2021-07-22
 */
public interface ApUserRealnameService extends IService<ApUserRealname> {

    Result authFail(AuthDTO dto);

    Result authPass(AuthDTO dto);
}

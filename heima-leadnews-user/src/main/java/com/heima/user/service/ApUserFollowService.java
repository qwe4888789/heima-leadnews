package com.heima.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.common.dtos.Result;
import com.heima.model.user.dtos.UserRelationDto;
import com.heima.model.user.pojos.ApUserFollow;

/**
 * <p>
 * APP用户关注信息表 服务类
 * </p>
 *
 * @author heima
 * @since 2021-07-22
 */
public interface ApUserFollowService extends IService<ApUserFollow> {

    Result userFollow(UserRelationDto dto);

    ApUserFollow findUserFollow(Integer userId, Integer followId);
}

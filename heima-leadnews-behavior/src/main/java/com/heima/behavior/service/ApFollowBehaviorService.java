package com.heima.behavior.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.behavior.dtos.FollowBehaviorDto;
import com.heima.model.behavior.pojos.ApFollowBehavior;

/**
 * <p>
 * APP关注行为表 服务类
 * </p>
 *
 * @author heima
 * @since 2021-08-01
 */
public interface ApFollowBehaviorService extends IService<ApFollowBehavior> {

    void followBahavior(FollowBehaviorDto followBehaviorDto);
}

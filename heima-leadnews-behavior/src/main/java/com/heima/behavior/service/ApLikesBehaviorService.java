package com.heima.behavior.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.common.dtos.Result;
import com.heima.model.behavior.dtos.LikesBehaviorDto;
import com.heima.model.behavior.pojos.ApLikesBehavior;
import com.heima.model.behavior.pojos.ApUnlikesBehavior;

/**
 * <p>
 * APP点赞行为表 服务类
 * </p>
 *
 * @author heima
 * @since 2021-08-01
 */
public interface ApLikesBehaviorService extends IService<ApLikesBehavior> {

    Result like(LikesBehaviorDto dto);

    ApLikesBehavior findLike(Integer entryId, Long articleId);
}

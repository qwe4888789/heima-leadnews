package com.heima.behavior.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.behavior.pojos.ApUnlikesBehavior;

/**
 * <p>
 * APP不喜欢行为表 服务类
 * </p>
 *
 * @author heima
 * @since 2021-08-01
 */
public interface ApUnlikesBehaviorService extends IService<ApUnlikesBehavior> {

    ApUnlikesBehavior findUnlike(Integer entryId, Long articleId);
}

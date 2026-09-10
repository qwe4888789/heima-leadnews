package com.heima.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.article.pojos.ApCollection;

/**
 * <p>
 * APP收藏信息表 服务类
 * </p>
 *
 * @author heima
 * @since 2021-07-22
 */
public interface ApCollectionService extends IService<ApCollection> {

    /**
     * 查询用户收藏记录
     */
    public ApCollection findCollection(Integer entryId,Long articleId);
}

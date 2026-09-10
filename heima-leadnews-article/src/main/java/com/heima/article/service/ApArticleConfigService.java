package com.heima.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.article.pojos.ApArticleConfig;

/**
 * <p>
 * APP已发布文章配置表 服务类
 * </p>
 *
 * @author heima
 * @since 2021-07-22
 */
public interface ApArticleConfigService extends IService<ApArticleConfig> {

    void updateIsDown(Long apArticleId, Integer enable);
}

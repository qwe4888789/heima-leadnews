package com.heima.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.common.dtos.Result;
import com.heima.model.article.dtos.ApArticleDto;
import com.heima.model.article.dtos.ApArticleInfoDto;
import com.heima.model.article.dtos.ArticleBehaviorDto;
import com.heima.model.article.pojos.ApArticle;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文章信息表，存储已发布的文章 服务类
 * </p>
 *
 * @author heima
 * @since 2021-07-22
 */
public interface ApArticleService extends IService<ApArticle> {

    Result<List<ApArticle>> loadApArticle(ApArticleDto dto, int type);

    Result<Map<String, Object>> loadArticleInfo(ApArticleInfoDto dto);

    Result<Map<String, Boolean>> loadArticleBehavior(ArticleBehaviorDto dto);
}

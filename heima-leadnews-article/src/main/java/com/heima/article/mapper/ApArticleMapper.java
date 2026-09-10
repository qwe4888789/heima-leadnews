package com.heima.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heima.model.article.dtos.ApArticleDto;
import com.heima.model.article.pojos.ApArticle;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 文章信息表，存储已发布的文章 Mapper 接口
 * </p>
 *
 * @author heima
 * @since 2021-07-22
 */
public interface ApArticleMapper extends BaseMapper<ApArticle> {

    List<ApArticle> loadApArticle(@Param("dto") ApArticleDto dto,@Param("type") int type);
}

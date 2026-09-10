package com.heima.article.feign;

import com.heima.common.dtos.Result;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.article.pojos.ApArticleConfig;
import com.heima.model.article.pojos.ApArticleContent;
import com.heima.model.article.pojos.ApAuthor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "leadnews-article",contextId = "ppArticleFeign")
public interface ApArticleFeign {

    /**
     * 添加文章
     * @param record
     */
    @PostMapping("/api/apArticle/save")
    public Result<ApArticle> saveApArticle(@RequestBody ApArticle record);

    /**
     * 添加文章配置
     * @param record
     */
    @PostMapping("/api/apArticleConfig/save")
    public Result<ApArticleConfig> saveApArticleConfig(@RequestBody ApArticleConfig record);

    /**
     * 添加文章内容
     * @param record
     */
    @PostMapping("/api/apArticleContent/save")
    public Result<ApArticleContent> saveApArticleContent(@RequestBody ApArticleContent record);


    /**
     * 查询全部数据
     */
    @GetMapping("/api/apArticle/list")
    public Result<List<ApArticle>> findAll();

    /**
     * 根据id查询文章
     */
    @GetMapping("/api/apArticle/findById/{id}")
    public ApArticle findById(@PathVariable("id") Long id);
}

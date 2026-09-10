package com.heima.article.controller;


import com.heima.article.service.ApArticleService;
import com.heima.common.controller.AbstractController;
import com.heima.common.dtos.Result;
import com.heima.model.article.dtos.ApArticleDto;
import com.heima.model.article.dtos.ApArticleInfoDto;
import com.heima.model.article.dtos.ArticleBehaviorDto;
import com.heima.model.article.pojos.ApArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
* <p>
* 文章信息表，存储已发布的文章 控制器</p>
* @author heima
* @since 2021-07-22
*/
@RestController
@RequestMapping("/api/apArticle")
public class ApArticleController extends AbstractController<ApArticle>{

    private ApArticleService apArticleService;

    //注入
    @Autowired
    public ApArticleController(ApArticleService apArticleService) {
        super(apArticleService);
        this.apArticleService=apArticleService;
    }


    /**
     * 加载首页文章
     */
    @PostMapping("/load")
    public Result<List<ApArticle>> load(@RequestBody ApArticleDto dto){
        return apArticleService.loadApArticle(dto,1); // 1  代表上拉
    }

    /**
     * 加载更多文章
     */
    @PostMapping("/loadmore")
    public Result<List<ApArticle>> loadmore(@RequestBody ApArticleDto dto){
        return apArticleService.loadApArticle(dto,1); // 1  代表上拉
    }

    /**
     * 加载最新文章
     */
    @PostMapping("/loadnew")
    public Result<List<ApArticle>> loadnew(@RequestBody ApArticleDto dto){
        return apArticleService.loadApArticle(dto,2); // 2  代表下拉
    }

    /**
     * 加载文章详情
     */
    @PostMapping("/load_article_info")
    public Result<Map<String,Object>> loadArticleInfo(@RequestBody ApArticleInfoDto dto){
        return apArticleService.loadArticleInfo(dto);
    }

    /**
     * 查询文章的行为数据，进行回显
     */
    @PostMapping("/load_article_behavior")
    public Result<Map<String,Boolean>> loadArticleBehavior(@RequestBody ArticleBehaviorDto dto){
        return apArticleService.loadArticleBehavior(dto);
    }

    /**
     * 根据id查询文章
     */
    @GetMapping("/findById/{id}")
    public ApArticle findById(@PathVariable("id") Long id){
        return apArticleService.getById(id);
    }
}


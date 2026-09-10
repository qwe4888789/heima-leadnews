package com.heima.search.controller;

import com.heima.common.dtos.Result;
import com.heima.model.search.dtos.UserSearchDto;
import com.heima.model.search.pojos.ArticleDocument;
import com.heima.search.service.ArticleSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 文章搜索
 */
@RestController
@RequestMapping("/api/article/search")
public class ArticleSearchController {
    @Autowired
    private ArticleSearchService articleSearchService;
    /**
     * 搜索
     */
    @PostMapping("/search")
    public Result<List<ArticleDocument>> articleSearch(@RequestBody UserSearchDto dto){
        return articleSearchService.articleSearch(dto);
    }
}

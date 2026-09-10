package com.heima.article.controller;


import com.heima.article.service.ApHotArticlesService;
import com.heima.common.controller.AbstractController;
import com.heima.model.article.pojos.ApHotArticles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 热文章表 控制器</p>
* @author heima
* @since 2021-07-22
*/
@RestController
@RequestMapping("/api/apHotArticles")
public class ApHotArticlesController extends AbstractController<ApHotArticles>{

    private ApHotArticlesService apHotArticlesService;

    //注入
    @Autowired
    public ApHotArticlesController(ApHotArticlesService apHotArticlesService) {
        super(apHotArticlesService);
        this.apHotArticlesService=apHotArticlesService;
    }

}


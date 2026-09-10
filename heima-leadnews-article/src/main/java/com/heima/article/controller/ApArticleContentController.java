package com.heima.article.controller;


import com.heima.article.service.ApArticleContentService;
import com.heima.common.controller.AbstractController;
import com.heima.model.article.pojos.ApArticleContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* APP已发布文章内容表 控制器</p>
* @author heima
* @since 2021-07-22
*/
@RestController
@RequestMapping("/api/apArticleContent")
public class ApArticleContentController extends AbstractController<ApArticleContent>{

    private ApArticleContentService apArticleContentService;

    //注入
    @Autowired
    public ApArticleContentController(ApArticleContentService apArticleContentService) {
        super(apArticleContentService);
        this.apArticleContentService=apArticleContentService;
    }

}


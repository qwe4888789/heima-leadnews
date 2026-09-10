package com.heima.article.controller;


import com.heima.article.service.ApArticleConfigService;
import com.heima.common.controller.AbstractController;
import com.heima.model.article.pojos.ApArticleConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* APP已发布文章配置表 控制器</p>
* @author heima
* @since 2021-07-22
*/
@RestController
@RequestMapping("/api/apArticleConfig")
public class ApArticleConfigController extends AbstractController<ApArticleConfig>{

    private ApArticleConfigService apArticleConfigService;

    //注入
    @Autowired
    public ApArticleConfigController(ApArticleConfigService apArticleConfigService) {
        super(apArticleConfigService);
        this.apArticleConfigService=apArticleConfigService;
    }

}


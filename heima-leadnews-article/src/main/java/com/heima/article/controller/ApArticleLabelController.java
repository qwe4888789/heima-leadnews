package com.heima.article.controller;


import com.heima.article.service.ApArticleLabelService;
import com.heima.common.controller.AbstractController;
import com.heima.model.article.pojos.ApArticleLabel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 文章标签信息表 控制器</p>
* @author heima
* @since 2021-07-22
*/
@RestController
@RequestMapping("/api/apArticleLabel")
public class ApArticleLabelController extends AbstractController<ApArticleLabel>{

    private ApArticleLabelService apArticleLabelService;

    //注入
    @Autowired
    public ApArticleLabelController(ApArticleLabelService apArticleLabelService) {
        super(apArticleLabelService);
        this.apArticleLabelService=apArticleLabelService;
    }

}


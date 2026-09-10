package com.heima.article.controller;


import com.heima.article.service.ApHotWordsService;
import com.heima.common.controller.AbstractController;
import com.heima.model.article.pojos.ApHotWords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 搜索热词表 控制器</p>
* @author heima
* @since 2021-07-22
*/
@RestController
@RequestMapping("/api/apHotWords")
public class ApHotWordsController extends AbstractController<ApHotWords>{

    private ApHotWordsService apHotWordsService;

    //注入
    @Autowired
    public ApHotWordsController(ApHotWordsService apHotWordsService) {
        super(apHotWordsService);
        this.apHotWordsService=apHotWordsService;
    }

}


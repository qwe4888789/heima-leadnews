package com.heima.article.controller;


import com.heima.article.service.ApAssociateWordsService;
import com.heima.common.controller.AbstractController;
import com.heima.model.article.pojos.ApAssociateWords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 联想词表 控制器</p>
* @author heima
* @since 2021-07-22
*/
@RestController
@RequestMapping("/api/apAssociateWords")
public class ApAssociateWordsController extends AbstractController<ApAssociateWords>{

    private ApAssociateWordsService apAssociateWordsService;

    //注入
    @Autowired
    public ApAssociateWordsController(ApAssociateWordsService apAssociateWordsService) {
        super(apAssociateWordsService);
        this.apAssociateWordsService=apAssociateWordsService;
    }

}


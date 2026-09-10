package com.heima.article.controller;


import com.heima.article.service.ApCollectionService;
import com.heima.common.controller.AbstractController;
import com.heima.model.article.pojos.ApCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* APP收藏信息表 控制器</p>
* @author heima
* @since 2021-07-22
*/
@RestController
@RequestMapping("/api/apCollection")
public class ApCollectionController extends AbstractController<ApCollection>{

    private ApCollectionService apCollectionService;

    //注入
    @Autowired
    public ApCollectionController(ApCollectionService apCollectionService) {
        super(apCollectionService);
        this.apCollectionService=apCollectionService;
    }

}


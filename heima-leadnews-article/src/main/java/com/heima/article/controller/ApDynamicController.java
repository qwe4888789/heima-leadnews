package com.heima.article.controller;


import com.heima.article.service.ApDynamicService;
import com.heima.common.controller.AbstractController;
import com.heima.model.article.pojos.ApDynamic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* APP用户动态信息表 控制器</p>
* @author heima
* @since 2021-07-22
*/
@RestController
@RequestMapping("/api/apDynamic")
public class ApDynamicController extends AbstractController<ApDynamic>{

    private ApDynamicService apDynamicService;

    //注入
    @Autowired
    public ApDynamicController(ApDynamicService apDynamicService) {
        super(apDynamicService);
        this.apDynamicService=apDynamicService;
    }

}


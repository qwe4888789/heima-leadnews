package com.heima.behavior.controller;


import com.heima.behavior.service.ApShowBehaviorService;
import com.heima.common.controller.AbstractController;
import com.heima.model.behavior.pojos.ApShowBehavior;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* APP文章展现行为表 控制器</p>
* @author heima
* @since 2021-08-01
*/
@RestController
@RequestMapping("/api/apShowBehavior")
public class ApShowBehaviorController extends AbstractController<ApShowBehavior>{

    private ApShowBehaviorService apShowBehaviorService;

    //注入
    @Autowired
    public ApShowBehaviorController(ApShowBehaviorService apShowBehaviorService) {
        super(apShowBehaviorService);
        this.apShowBehaviorService=apShowBehaviorService;
    }

}


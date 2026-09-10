package com.heima.behavior.controller;


import com.heima.behavior.service.ApForwardBehaviorService;
import com.heima.common.controller.AbstractController;
import com.heima.model.behavior.pojos.ApForwardBehavior;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* APP转发行为表 控制器</p>
* @author heima
* @since 2021-08-01
*/
@RestController
@RequestMapping("/api/apForwardBehavior")
public class ApForwardBehaviorController extends AbstractController<ApForwardBehavior>{

    private ApForwardBehaviorService apForwardBehaviorService;

    //注入
    @Autowired
    public ApForwardBehaviorController(ApForwardBehaviorService apForwardBehaviorService) {
        super(apForwardBehaviorService);
        this.apForwardBehaviorService=apForwardBehaviorService;
    }

}


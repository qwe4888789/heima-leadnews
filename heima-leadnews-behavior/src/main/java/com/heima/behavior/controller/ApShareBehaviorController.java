package com.heima.behavior.controller;


import com.heima.behavior.service.ApShareBehaviorService;
import com.heima.common.controller.AbstractController;
import com.heima.model.behavior.pojos.ApShareBehavior;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* APP分享行为表 控制器</p>
* @author heima
* @since 2021-08-01
*/
@RestController
@RequestMapping("/api/apShareBehavior")
public class ApShareBehaviorController extends AbstractController<ApShareBehavior>{

    private ApShareBehaviorService apShareBehaviorService;

    //注入
    @Autowired
    public ApShareBehaviorController(ApShareBehaviorService apShareBehaviorService) {
        super(apShareBehaviorService);
        this.apShareBehaviorService=apShareBehaviorService;
    }

}


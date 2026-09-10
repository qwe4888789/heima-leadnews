package com.heima.behavior.controller;


import com.heima.behavior.service.ApFollowBehaviorService;
import com.heima.common.controller.AbstractController;
import com.heima.model.behavior.pojos.ApFollowBehavior;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* APP关注行为表 控制器</p>
* @author heima
* @since 2021-08-01
*/
@RestController
@RequestMapping("/api/apFollowBehavior")
public class ApFollowBehaviorController extends AbstractController<ApFollowBehavior>{

    private ApFollowBehaviorService apFollowBehaviorService;

    //注入
    @Autowired
    public ApFollowBehaviorController(ApFollowBehaviorService apFollowBehaviorService) {
        super(apFollowBehaviorService);
        this.apFollowBehaviorService=apFollowBehaviorService;
    }

}


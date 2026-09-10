package com.heima.behavior.controller;


import com.heima.behavior.service.ApReadBehaviorService;
import com.heima.common.controller.AbstractController;
import com.heima.common.dtos.Result;
import com.heima.model.behavior.dtos.ReadBehaviorDto;
import com.heima.model.behavior.pojos.ApReadBehavior;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* APP阅读行为表 控制器</p>
* @author heima
* @since 2021-08-01
*/
@RestController
@RequestMapping("/api/apReadBehavior")
public class ApReadBehaviorController extends AbstractController<ApReadBehavior>{

    private ApReadBehaviorService apReadBehaviorService;

    //注入
    @Autowired
    public ApReadBehaviorController(ApReadBehaviorService apReadBehaviorService) {
        super(apReadBehaviorService);
        this.apReadBehaviorService=apReadBehaviorService;
    }

    /**
     * 阅读行为
     */
    @PostMapping
    public Result read(@RequestBody ReadBehaviorDto dto){
        return apReadBehaviorService.read(dto);
    }
}


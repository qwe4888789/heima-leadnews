package com.heima.user.controller;


import com.heima.common.controller.AbstractController;
import com.heima.model.user.pojos.ApUserMessage;
import com.heima.user.service.ApUserMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* APP用户消息通知信息表 控制器</p>
* @author heima
* @since 2021-07-22
*/
@RestController
@RequestMapping("/api/apUserMessage")
public class ApUserMessageController extends AbstractController<ApUserMessage>{

    private ApUserMessageService apUserMessageService;

    //注入
    @Autowired
    public ApUserMessageController(ApUserMessageService apUserMessageService) {
        super(apUserMessageService);
        this.apUserMessageService=apUserMessageService;
    }

}


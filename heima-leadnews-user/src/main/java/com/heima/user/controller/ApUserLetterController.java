package com.heima.user.controller;


import com.heima.common.controller.AbstractController;
import com.heima.model.user.pojos.ApUserLetter;
import com.heima.user.service.ApUserLetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* APP用户私信信息表 控制器</p>
* @author heima
* @since 2021-07-22
*/
@RestController
@RequestMapping("/api/apUserLetter")
public class ApUserLetterController extends AbstractController<ApUserLetter>{

    private ApUserLetterService apUserLetterService;

    //注入
    @Autowired
    public ApUserLetterController(ApUserLetterService apUserLetterService) {
        super(apUserLetterService);
        this.apUserLetterService=apUserLetterService;
    }

}


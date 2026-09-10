package com.heima.user.controller;


import com.heima.common.controller.AbstractController;
import com.heima.model.user.pojos.ApUserIdentity;
import com.heima.user.service.ApUserIdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* APP身份认证信息表 控制器</p>
* @author heima
* @since 2021-07-22
*/
@RestController
@RequestMapping("/api/apUserIdentity")
public class ApUserIdentityController extends AbstractController<ApUserIdentity>{

    private ApUserIdentityService apUserIdentityService;

    //注入
    @Autowired
    public ApUserIdentityController(ApUserIdentityService apUserIdentityService) {
        super(apUserIdentityService);
        this.apUserIdentityService=apUserIdentityService;
    }

}


package com.heima.admin.controller;


import com.heima.admin.service.AdUserLoginService;
import com.heima.common.controller.AbstractController;
import com.heima.model.admin.pojos.AdUserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 管理员登录行为信息表 控制器</p>
* @author heima
* @since 2021-07-21
*/
@RestController
@RequestMapping("/api/adUserLogin")
public class AdUserLoginController extends AbstractController<AdUserLogin>{

    private AdUserLoginService adUserLoginService;

    //注入
    @Autowired
    public AdUserLoginController(AdUserLoginService adUserLoginService) {
        super(adUserLoginService);
        this.adUserLoginService=adUserLoginService;
    }

}


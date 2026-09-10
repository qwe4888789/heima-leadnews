package com.heima.admin.controller;


import com.heima.admin.service.AdUserService;
import com.heima.common.controller.AbstractController;
import com.heima.model.admin.pojos.AdUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 管理员用户信息表 控制器</p>
* @author heima
* @since 2021-07-21
*/
@RestController
@RequestMapping("/api/adUser")
public class AdUserController extends AbstractController<AdUser>{

    private AdUserService adUserService;

    //注入
    @Autowired
    public AdUserController(AdUserService adUserService) {
        super(adUserService);
        this.adUserService=adUserService;
    }

}


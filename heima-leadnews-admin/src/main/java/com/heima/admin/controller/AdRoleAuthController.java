package com.heima.admin.controller;


import com.heima.admin.service.AdRoleAuthService;
import com.heima.common.controller.AbstractController;
import com.heima.model.admin.pojos.AdRoleAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 角色权限信息表 控制器</p>
* @author heima
* @since 2021-07-21
*/
@RestController
@RequestMapping("/api/adRoleAuth")
public class AdRoleAuthController extends AbstractController<AdRoleAuth>{

    private AdRoleAuthService adRoleAuthService;

    //注入
    @Autowired
    public AdRoleAuthController(AdRoleAuthService adRoleAuthService) {
        super(adRoleAuthService);
        this.adRoleAuthService=adRoleAuthService;
    }

}


package com.heima.admin.controller;


import com.heima.admin.service.AdRoleService;
import com.heima.common.controller.AbstractController;
import com.heima.model.admin.pojos.AdRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 角色信息表 控制器</p>
* @author heima
* @since 2021-07-21
*/
@RestController
@RequestMapping("/api/adRole")
public class AdRoleController extends AbstractController<AdRole>{

    private AdRoleService adRoleService;

    //注入
    @Autowired
    public AdRoleController(AdRoleService adRoleService) {
        super(adRoleService);
        this.adRoleService=adRoleService;
    }

}


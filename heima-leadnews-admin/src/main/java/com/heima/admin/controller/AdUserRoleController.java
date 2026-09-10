package com.heima.admin.controller;


import com.heima.admin.service.AdUserRoleService;
import com.heima.common.controller.AbstractController;
import com.heima.model.admin.pojos.AdUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 管理员角色信息表 控制器</p>
* @author heima
* @since 2021-07-21
*/
@RestController
@RequestMapping("/api/adUserRole")
public class AdUserRoleController extends AbstractController<AdUserRole>{

    private AdUserRoleService adUserRoleService;

    //注入
    @Autowired
    public AdUserRoleController(AdUserRoleService adUserRoleService) {
        super(adUserRoleService);
        this.adUserRoleService=adUserRoleService;
    }

}


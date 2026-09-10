package com.heima.admin.controller;


import com.heima.admin.service.AdMenuService;
import com.heima.common.controller.AbstractController;
import com.heima.model.admin.pojos.AdMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 菜单资源信息表 控制器</p>
* @author heima
* @since 2021-07-21
*/
@RestController
@RequestMapping("/api/adMenu")
public class AdMenuController extends AbstractController<AdMenu>{

    private AdMenuService adMenuService;

    //注入
    @Autowired
    public AdMenuController(AdMenuService adMenuService) {
        super(adMenuService);
        this.adMenuService=adMenuService;
    }

}


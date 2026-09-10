package com.heima.wemedia.controller;


import com.heima.common.controller.AbstractController;
import com.heima.model.wemedia.pojos.WmUserAuth;
import com.heima.wemedia.service.WmUserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 自媒体子账号权限信息表 控制器</p>
* @author heima
* @since 2021-07-22
*/
@RestController
@RequestMapping("/api/wmUserAuth")
public class WmUserAuthController extends AbstractController<WmUserAuth>{

    private WmUserAuthService wmUserAuthService;

    //注入
    @Autowired
    public WmUserAuthController(WmUserAuthService wmUserAuthService) {
        super(wmUserAuthService);
        this.wmUserAuthService=wmUserAuthService;
    }

}


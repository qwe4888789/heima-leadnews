package com.heima.admin.controller;


import com.heima.admin.service.AdSensitiveService;
import com.heima.common.controller.AbstractController;
import com.heima.model.admin.pojos.AdSensitive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 敏感词信息表 控制器</p>
* @author heima
* @since 2021-07-21
*/
@RestController
@RequestMapping("/api/adSensitive")
public class AdSensitiveController extends AbstractController<AdSensitive>{

    private AdSensitiveService adSensitiveService;

    //注入
    @Autowired
    public AdSensitiveController(AdSensitiveService adSensitiveService) {
        super(adSensitiveService);
        this.adSensitiveService=adSensitiveService;
    }

}


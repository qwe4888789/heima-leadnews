package com.heima.admin.controller;


import com.heima.admin.service.AdUserOpertionService;
import com.heima.common.controller.AbstractController;
import com.heima.model.admin.pojos.AdUserOpertion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 管理员操作行为信息表 控制器</p>
* @author heima
* @since 2021-07-21
*/
@RestController
@RequestMapping("/api/adUserOpertion")
public class AdUserOpertionController extends AbstractController<AdUserOpertion>{

    private AdUserOpertionService adUserOpertionService;

    //注入
    @Autowired
    public AdUserOpertionController(AdUserOpertionService adUserOpertionService) {
        super(adUserOpertionService);
        this.adUserOpertionService=adUserOpertionService;
    }

}


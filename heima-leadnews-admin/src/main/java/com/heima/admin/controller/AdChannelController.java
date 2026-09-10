package com.heima.admin.controller;


import com.heima.admin.service.AdChannelService;
import com.heima.common.controller.AbstractController;
import com.heima.model.admin.pojos.AdChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 频道信息表 控制器</p>
* @author heima
* @since 2021-07-21
*/
@RestController
@RequestMapping("/api/adChannel")
public class AdChannelController extends AbstractController<AdChannel>{

    private AdChannelService adChannelService;

    //注入
    @Autowired
    public AdChannelController(AdChannelService adChannelService) {
        super(adChannelService);
        this.adChannelService=adChannelService;
    }

}


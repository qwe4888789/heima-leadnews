package com.heima.admin.controller;


import com.heima.admin.service.AdChannelLabelService;
import com.heima.common.controller.AbstractController;
import com.heima.model.admin.pojos.AdChannelLabel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 频道标签信息表 控制器</p>
* @author heima
* @since 2021-07-21
*/
@RestController
@RequestMapping("/api/adChannelLabel")
public class AdChannelLabelController extends AbstractController<AdChannelLabel>{

    private AdChannelLabelService adChannelLabelService;

    //注入
    @Autowired
    public AdChannelLabelController(AdChannelLabelService adChannelLabelService) {
        super(adChannelLabelService);
        this.adChannelLabelService=adChannelLabelService;
    }

}


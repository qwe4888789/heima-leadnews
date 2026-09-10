package com.heima.admin.controller;


import com.heima.admin.service.AdLabelService;
import com.heima.common.controller.AbstractController;
import com.heima.model.admin.pojos.AdLabel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 标签信息表 控制器</p>
* @author heima
* @since 2021-07-21
*/
@RestController
@RequestMapping("/api/adLabel")
public class AdLabelController extends AbstractController<AdLabel>{

    private AdLabelService adLabelService;

    //注入
    @Autowired
    public AdLabelController(AdLabelService adLabelService) {
        super(adLabelService);
        this.adLabelService=adLabelService;
    }

}


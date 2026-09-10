package com.heima.admin.controller;


import com.heima.admin.service.AdFunctionService;
import com.heima.common.controller.AbstractController;
import com.heima.model.admin.pojos.AdFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 页面功能信息表 控制器</p>
* @author heima
* @since 2021-07-21
*/
@RestController
@RequestMapping("/api/adFunction")
public class AdFunctionController extends AbstractController<AdFunction>{

    private AdFunctionService adFunctionService;

    //注入
    @Autowired
    public AdFunctionController(AdFunctionService adFunctionService) {
        super(adFunctionService);
        this.adFunctionService=adFunctionService;
    }

}


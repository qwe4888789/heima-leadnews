package com.heima.user.controller;


import com.heima.common.controller.AbstractController;
import com.heima.model.user.pojos.ApUserDynamicList;
import com.heima.user.service.ApUserDynamicListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* APP用户动态列表 控制器</p>
* @author heima
* @since 2021-07-22
*/
@RestController
@RequestMapping("/api/apUserDynamicList")
public class ApUserDynamicListController extends AbstractController<ApUserDynamicList>{

    private ApUserDynamicListService apUserDynamicListService;

    //注入
    @Autowired
    public ApUserDynamicListController(ApUserDynamicListService apUserDynamicListService) {
        super(apUserDynamicListService);
        this.apUserDynamicListService=apUserDynamicListService;
    }

}


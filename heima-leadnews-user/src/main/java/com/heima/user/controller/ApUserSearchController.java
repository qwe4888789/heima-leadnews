package com.heima.user.controller;


import com.heima.common.controller.AbstractController;
import com.heima.model.user.pojos.ApUserSearch;
import com.heima.user.service.ApUserSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* APP用户搜索信息表 控制器</p>
* @author heima
* @since 2021-07-22
*/
@RestController
@RequestMapping("/api/apUserSearch")
public class ApUserSearchController extends AbstractController<ApUserSearch>{

    private ApUserSearchService apUserSearchService;

    //注入
    @Autowired
    public ApUserSearchController(ApUserSearchService apUserSearchService) {
        super(apUserSearchService);
        this.apUserSearchService=apUserSearchService;
    }

}


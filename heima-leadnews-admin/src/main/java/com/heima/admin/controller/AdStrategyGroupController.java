package com.heima.admin.controller;


import com.heima.admin.service.AdStrategyGroupService;
import com.heima.common.controller.AbstractController;
import com.heima.model.admin.pojos.AdStrategyGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 推荐策略分组信息表 控制器</p>
* @author heima
* @since 2021-07-21
*/
@RestController
@RequestMapping("/api/adStrategyGroup")
public class AdStrategyGroupController extends AbstractController<AdStrategyGroup>{

    private AdStrategyGroupService adStrategyGroupService;

    //注入
    @Autowired
    public AdStrategyGroupController(AdStrategyGroupService adStrategyGroupService) {
        super(adStrategyGroupService);
        this.adStrategyGroupService=adStrategyGroupService;
    }

}


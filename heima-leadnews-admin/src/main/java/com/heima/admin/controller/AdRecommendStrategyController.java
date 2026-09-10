package com.heima.admin.controller;


import com.heima.admin.service.AdRecommendStrategyService;
import com.heima.common.controller.AbstractController;
import com.heima.model.admin.pojos.AdRecommendStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 推荐策略信息表 控制器</p>
* @author heima
* @since 2021-07-21
*/
@RestController
@RequestMapping("/api/adRecommendStrategy")
public class AdRecommendStrategyController extends AbstractController<AdRecommendStrategy>{

    private AdRecommendStrategyService adRecommendStrategyService;

    //注入
    @Autowired
    public AdRecommendStrategyController(AdRecommendStrategyService adRecommendStrategyService) {
        super(adRecommendStrategyService);
        this.adRecommendStrategyService=adRecommendStrategyService;
    }

}


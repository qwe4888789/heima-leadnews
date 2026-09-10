package com.heima.admin.controller;


import com.heima.admin.service.AdVistorStatisticsService;
import com.heima.common.controller.AbstractController;
import com.heima.model.admin.pojos.AdVistorStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 访问数据统计表 控制器</p>
* @author heima
* @since 2021-07-21
*/
@RestController
@RequestMapping("/api/adVistorStatistics")
public class AdVistorStatisticsController extends AbstractController<AdVistorStatistics>{

    private AdVistorStatisticsService adVistorStatisticsService;

    //注入
    @Autowired
    public AdVistorStatisticsController(AdVistorStatisticsService adVistorStatisticsService) {
        super(adVistorStatisticsService);
        this.adVistorStatisticsService=adVistorStatisticsService;
    }

}


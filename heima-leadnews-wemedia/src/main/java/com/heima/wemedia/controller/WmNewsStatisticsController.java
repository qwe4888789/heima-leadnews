package com.heima.wemedia.controller;


import com.heima.common.controller.AbstractController;
import com.heima.model.wemedia.pojos.WmNewsStatistics;
import com.heima.wemedia.service.WmNewsStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 自媒体图文数据统计表 控制器</p>
* @author heima
* @since 2021-07-22
*/
@RestController
@RequestMapping("/api/wmNewsStatistics")
public class WmNewsStatisticsController extends AbstractController<WmNewsStatistics>{

    private WmNewsStatisticsService wmNewsStatisticsService;

    //注入
    @Autowired
    public WmNewsStatisticsController(WmNewsStatisticsService wmNewsStatisticsService) {
        super(wmNewsStatisticsService);
        this.wmNewsStatisticsService=wmNewsStatisticsService;
    }

}


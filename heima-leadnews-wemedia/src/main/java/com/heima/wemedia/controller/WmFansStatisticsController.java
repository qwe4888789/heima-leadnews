package com.heima.wemedia.controller;


import com.heima.common.controller.AbstractController;
import com.heima.model.wemedia.pojos.WmFansStatistics;
import com.heima.wemedia.service.WmFansStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 自媒体粉丝数据统计表 控制器</p>
* @author heima
* @since 2021-07-22
*/
@RestController
@RequestMapping("/api/wmFansStatistics")
public class WmFansStatisticsController extends AbstractController<WmFansStatistics>{

    private WmFansStatisticsService wmFansStatisticsService;

    //注入
    @Autowired
    public WmFansStatisticsController(WmFansStatisticsService wmFansStatisticsService) {
        super(wmFansStatisticsService);
        this.wmFansStatisticsService=wmFansStatisticsService;
    }

}


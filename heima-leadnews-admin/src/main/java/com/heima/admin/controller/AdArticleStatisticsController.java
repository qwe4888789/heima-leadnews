package com.heima.admin.controller;


import com.heima.admin.service.AdArticleStatisticsService;
import com.heima.common.controller.AbstractController;
import com.heima.model.admin.pojos.AdArticleStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 文章数据统计表 控制器</p>
* @author heima
* @since 2021-07-21
*/
@RestController
@RequestMapping("/api/adArticleStatistics")
public class AdArticleStatisticsController extends AbstractController<AdArticleStatistics>{

    private AdArticleStatisticsService adArticleStatisticsService;

    //注入
    @Autowired
    public AdArticleStatisticsController(AdArticleStatisticsService adArticleStatisticsService) {
        super(adArticleStatisticsService);
        this.adArticleStatisticsService=adArticleStatisticsService;
    }

}


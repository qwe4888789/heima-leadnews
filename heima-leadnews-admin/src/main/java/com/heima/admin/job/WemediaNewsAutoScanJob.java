package com.heima.admin.job;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.heima.admin.service.WemediaNewsScanService;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.wemedia.feign.WmNewsFeign;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自媒体文章的定时发布任务
 */
@Component
@Slf4j
public class WemediaNewsAutoScanJob {
    @Autowired
    private WmNewsFeign wmNewsFeign;

    @Autowired
    private WemediaNewsScanService wemediaNewsScanService;

    /**
     * 文章定时发布任务
     */
    @XxlJob("wemediaAutoScanJob")
    public ReturnT<String> demoJobHandler(String param) throws Exception {
        log.info("自媒体文章的定时发布任务 {}");

        //1.查询待发布的文章
        List<WmNews> wmNews = wmNewsFeign.findRelease().getData();
        if(CollectionUtils.isNotEmpty(wmNews)){
            //2.发布文章
            wemediaNewsScanService.scahedulePublishNews(wmNews);
        }

        return ReturnT.SUCCESS;
    }


}

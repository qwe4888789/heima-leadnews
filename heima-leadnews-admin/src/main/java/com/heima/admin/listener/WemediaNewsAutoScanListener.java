package com.heima.admin.listener;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.heima.admin.service.WemediaNewsScanService;
import com.heima.common.constants.MQConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 自媒体文章自动审核监听器
 */
@Component
@Slf4j
public class WemediaNewsAutoScanListener {

    @Autowired
    private WemediaNewsScanService wemediaNewsScanService;

    @KafkaListener(topics = MQConstants.WM_NEWS_AUTO_SCAN_TOPIC)
    public void newsAutoScan(ConsumerRecord<String,String> record){
        log.info("文章自动审核开始了...");
        if(record!=null){
            String articleId = record.value();
            if(StringUtils.isNotEmpty(articleId)){
                wemediaNewsScanService.autoScanByMediaNewsId(Integer.valueOf(articleId));
            }
        }
    }
}

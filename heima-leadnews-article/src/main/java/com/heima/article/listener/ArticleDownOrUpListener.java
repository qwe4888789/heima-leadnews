package com.heima.article.listener;

import com.heima.article.service.ApArticleConfigService;
import com.heima.common.constants.MQConstants;
import com.heima.utils.common.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 文章上下架状态同步监听
 */
@Component
@Slf4j
public class ArticleDownOrUpListener {

    @Autowired
    private ApArticleConfigService apArticleConfigService;

    @KafkaListener(topics = MQConstants.WM_NEWS_UP_OR_DOWN_TOPIC)
    public void handleDownOrUp(ConsumerRecord<String,String> record){
        log.info("文章上下架状态同步 {}");
        if(record!=null){
            String value = record.value();
            //转换Map对象
            Map msgMap = JsonUtils.toBean(value, Map.class);
            if(msgMap!=null){
                Integer enable = (Integer)msgMap.get("enable");
                Long apArticleId = (Long)msgMap.get("apArticleId");
                apArticleConfigService.updateIsDown(apArticleId,enable);
            }
        }
    }
}

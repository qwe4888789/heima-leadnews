package com.heima.search.listener;

import com.heima.common.constants.MQConstants;
import com.heima.search.service.ArticleSearchService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 文章索引同步监听
 */
@Component
@Slf4j
public class ArticleDocumentListener {

    @Autowired
    private ArticleSearchService articleSearchService;

    /**
     * 文章上架->同步索引到ES
     */
    @KafkaListener(topics = MQConstants.WM_NEW_UP_ES_TOPIC)
    public void handleUp(ConsumerRecord<String,String> record){
        log.info("文章开始上架 {}");
        if(record!=null){
            String articleId = record.value();
            articleSearchService.saveToES(articleId);
        }
    }


    /**
     * 文章下架->从ES删除索引
     */
    @KafkaListener(topics = MQConstants.WM_NEW_DOWN_ES_TOPIC)
    public void handleDown(ConsumerRecord<String,String> record){
        log.info("文章开始下架 {}");
        if(record!=null){
            String articleId = record.value();
            articleSearchService.removeFromES(articleId);
        }
    }
}

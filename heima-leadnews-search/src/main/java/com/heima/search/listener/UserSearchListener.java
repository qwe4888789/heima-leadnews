package com.heima.search.listener;

import com.heima.common.constants.MQConstants;
import com.heima.search.service.ApUserSearchService;
import com.heima.utils.common.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 用户搜索记录监听
 */
@Component
@Slf4j
public class UserSearchListener {

    @Autowired
    private ApUserSearchService apUserSearchService;

    @KafkaListener(topics = MQConstants.USER_SEARCH_TOPIC)
    public void handleSearch(ConsumerRecord<String,String> record){
        log.info("开始记录用户搜索数据 {}");
        if(record!=null){
            String value = record.value();
            Map<String,Object> msgMap = JsonUtils.toBean(value, Map.class);
            apUserSearchService.saveUserSearch(msgMap);
        }
    }
}

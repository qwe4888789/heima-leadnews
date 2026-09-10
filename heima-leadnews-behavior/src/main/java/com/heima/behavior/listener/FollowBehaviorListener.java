package com.heima.behavior.listener;

import com.heima.behavior.service.ApFollowBehaviorService;
import com.heima.common.constants.MQConstants;
import com.heima.model.behavior.dtos.FollowBehaviorDto;
import com.heima.utils.common.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 关注行为监听
 */
@Component
@Slf4j
public class FollowBehaviorListener {
    @Autowired
    private ApFollowBehaviorService apFollowBehaviorService;

    @KafkaListener(topics = MQConstants.FOLLOW_BEHAVIOR_TOPIC)
    public void followBehavior(ConsumerRecord<String,String> record){
        log.info("关注行为开始记录 {}");
        if(record!=null){
            String value = record.value();
            //转换为对象
            FollowBehaviorDto followBehaviorDto = JsonUtils.toBean(value, FollowBehaviorDto.class);
            apFollowBehaviorService.followBahavior(followBehaviorDto);
        }
    }
}

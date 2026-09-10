package com.heima.behavior.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.behavior.mapper.ApFollowBehaviorMapper;
import com.heima.behavior.service.ApBehaviorEntryService;
import com.heima.behavior.service.ApFollowBehaviorService;
import com.heima.common.exception.AppHttpCodeEnum;
import com.heima.common.exception.LeadNewsException;
import com.heima.model.behavior.dtos.FollowBehaviorDto;
import com.heima.model.behavior.pojos.ApBehaviorEntry;
import com.heima.model.behavior.pojos.ApFollowBehavior;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 * APP关注行为表 服务实现类
 * </p>
 *
 * @author heima
 * @since 2021-08-01
 */
@Service
@Transactional
public class ApFollowBehaviorServiceImpl extends ServiceImpl<ApFollowBehaviorMapper, ApFollowBehavior> implements ApFollowBehaviorService {
    @Autowired
    private ApBehaviorEntryService apBehaviorEntryService;

    @Override
    public void followBahavior(FollowBehaviorDto dto) {
        //1.查询行为实体对象
        ApBehaviorEntry behaviorEntry = apBehaviorEntryService.findByUserIdOrEquipmentId(dto.getUserId(), null);

        if(behaviorEntry==null)return;

        try {
            //2.保存关注行为记录
            ApFollowBehavior followBehavior = new ApFollowBehavior();
            followBehavior.setEntryId(behaviorEntry.getId());
            followBehavior.setArticleId(dto.getArticleId());
            followBehavior.setFollowId(dto.getFollowId());
            followBehavior.setCreatedTime(new Date());
            save(followBehavior);
        } catch (Exception e) {
            e.printStackTrace();
            throw new LeadNewsException(AppHttpCodeEnum.SERVER_ERROR);
        }

    }
}

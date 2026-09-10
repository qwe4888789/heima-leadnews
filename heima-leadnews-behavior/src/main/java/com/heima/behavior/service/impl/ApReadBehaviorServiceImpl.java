package com.heima.behavior.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.behavior.mapper.ApReadBehaviorMapper;
import com.heima.behavior.service.ApBehaviorEntryService;
import com.heima.behavior.service.ApReadBehaviorService;
import com.heima.common.dtos.Result;
import com.heima.common.exception.AppHttpCodeEnum;
import com.heima.common.exception.LeadNewsException;
import com.heima.model.behavior.dtos.ReadBehaviorDto;
import com.heima.model.behavior.pojos.ApBehaviorEntry;
import com.heima.model.behavior.pojos.ApReadBehavior;
import com.heima.model.user.pojos.ApUser;
import com.heima.utils.common.ThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 * APP阅读行为表 服务实现类
 * </p>
 *
 * @author heima
 * @since 2021-08-01
 */
@Service
@Transactional
public class ApReadBehaviorServiceImpl extends ServiceImpl<ApReadBehaviorMapper, ApReadBehavior> implements ApReadBehaviorService {
    @Autowired
    private ApBehaviorEntryService apBehaviorEntryService;

    @Override
    public Result read(ReadBehaviorDto dto) {
        //查询登录用户信息
        ApUser apUser = (ApUser)ThreadLocalUtils.get();
        Integer userId = null;
        if(apUser!=null){
            userId = apUser.getId();
        }
        //查询行为实体
        ApBehaviorEntry behaviorEntry = apBehaviorEntryService.findByUserIdOrEquipmentId(userId, dto.getEquipmentId());

        if(behaviorEntry==null)return Result.ok();

        try {
            //查询阅读记录
            QueryWrapper<ApReadBehavior> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("entry_id",behaviorEntry.getId());
            queryWrapper.eq("article_id",dto.getArticleId());
            ApReadBehavior readBehavior = getOne(queryWrapper);

            if(readBehavior==null){
                //保存阅读行为
                readBehavior = new ApReadBehavior();
                readBehavior.setEntryId(behaviorEntry.getId());
                readBehavior.setArticleId(dto.getArticleId());
                readBehavior.setCount(1);
                readBehavior.setReadDuration(dto.getReadDuration());
                readBehavior.setLoadDuration(dto.getLoadDuration());
                readBehavior.setPercentage(dto.getPercentage());
                readBehavior.setCreatedTime(new Date());
                save(readBehavior);

            }else{
                //更新阅读行为
                readBehavior.setCount(readBehavior.getCount()+1);
                readBehavior.setReadDuration(readBehavior.getReadDuration()+dto.getReadDuration());
                readBehavior.setPercentage(dto.getPercentage());
                readBehavior.setLoadDuration(dto.getLoadDuration());
                readBehavior.setUpdatedTime(new Date());
                updateById(readBehavior);
            }

            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            throw new LeadNewsException(AppHttpCodeEnum.SERVER_ERROR);
        }
    }
}

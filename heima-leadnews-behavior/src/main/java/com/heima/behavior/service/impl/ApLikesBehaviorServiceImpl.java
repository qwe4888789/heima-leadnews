package com.heima.behavior.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.behavior.mapper.ApLikesBehaviorMapper;
import com.heima.behavior.service.ApBehaviorEntryService;
import com.heima.behavior.service.ApLikesBehaviorService;
import com.heima.common.dtos.Result;
import com.heima.common.exception.AppHttpCodeEnum;
import com.heima.common.exception.LeadNewsException;
import com.heima.model.behavior.dtos.LikesBehaviorDto;
import com.heima.model.behavior.pojos.ApBehaviorEntry;
import com.heima.model.behavior.pojos.ApLikesBehavior;
import com.heima.model.behavior.pojos.ApUnlikesBehavior;
import com.heima.model.user.pojos.ApUser;
import com.heima.utils.common.ThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 * APP点赞行为表 服务实现类
 * </p>
 *
 * @author heima
 * @since 2021-08-01
 */
@Service
@Transactional
public class ApLikesBehaviorServiceImpl extends ServiceImpl<ApLikesBehaviorMapper, ApLikesBehavior> implements ApLikesBehaviorService {
    @Autowired
    private ApBehaviorEntryService apBehaviorEntryService;

    @Override
    public Result like(LikesBehaviorDto dto) {
        //获取登录用户信息
        ApUser apUser = (ApUser)ThreadLocalUtils.get();
        Integer userId = null;
        if(apUser!=null){
            userId = apUser.getId();
        }

        //查询行为实体对象
        ApBehaviorEntry behaviorEntry = apBehaviorEntryService.findByUserIdOrEquipmentId(userId, dto.getEquipmentId());
        if(behaviorEntry==null){
            throw new LeadNewsException(500,"点赞失败");
        }

        try {
            //查询点赞记录
            QueryWrapper<ApLikesBehavior> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("entry_id",behaviorEntry.getId());
            queryWrapper.eq("article_id",dto.getArticleId());

            ApLikesBehavior apLikesBehavior = getOne(queryWrapper);

            if(apLikesBehavior==null && dto.getOperation().equals(0)){
                //添加点赞记录
                apLikesBehavior = new ApLikesBehavior();
                apLikesBehavior.setEntryId(behaviorEntry.getId());
                apLikesBehavior.setArticleId(dto.getArticleId());
                apLikesBehavior.setType(0);
                apLikesBehavior.setOperation(0);
                apLikesBehavior.setCreatedTime(new Date());
                save(apLikesBehavior);

            }else{
                //更新点赞记录
                apLikesBehavior.setOperation(dto.getOperation());
                apLikesBehavior.setCreatedTime(new Date());
                updateById(apLikesBehavior);
            }
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            throw new LeadNewsException(AppHttpCodeEnum.SERVER_ERROR);
        }
    }

    @Override
    public ApLikesBehavior findLike(Integer entryId, Long articleId) {
        QueryWrapper<ApLikesBehavior> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("entry_id",entryId);
        queryWrapper.eq("article_id",articleId);
        return getOne(queryWrapper);
    }
}

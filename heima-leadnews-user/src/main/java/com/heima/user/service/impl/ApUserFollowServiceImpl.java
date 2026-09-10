package com.heima.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.article.feign.ApAuthorFeign;
import com.heima.common.constants.MQConstants;
import com.heima.common.dtos.Result;
import com.heima.common.exception.AppHttpCodeEnum;
import com.heima.common.exception.LeadNewsException;
import com.heima.model.article.pojos.ApAuthor;
import com.heima.model.behavior.dtos.FollowBehaviorDto;
import com.heima.model.user.dtos.UserRelationDto;
import com.heima.model.user.pojos.ApUser;
import com.heima.model.user.pojos.ApUserFan;
import com.heima.model.user.pojos.ApUserFollow;
import com.heima.user.mapper.ApUserFanMapper;
import com.heima.user.mapper.ApUserFollowMapper;
import com.heima.user.mapper.ApUserMapper;
import com.heima.user.service.ApUserFollowService;
import com.heima.utils.common.JsonUtils;
import com.heima.utils.common.ThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 * APP用户关注信息表 服务实现类
 * </p>
 *
 * @author heima
 * @since 2021-07-22
 */
@Service
@Transactional
public class ApUserFollowServiceImpl extends ServiceImpl<ApUserFollowMapper, ApUserFollow> implements ApUserFollowService {
    @Autowired
    private ApAuthorFeign apAuthorFeign;
    @Autowired
    private ApUserFanMapper apUserFanMapper;
    @Autowired
    private ApUserMapper apUserMapper;
    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Override
    public Result userFollow(UserRelationDto dto) {
        //1.判断用户是登录
        ApUser apUser = (ApUser)ThreadLocalUtils.get();
        if(apUser==null){
            throw new LeadNewsException(AppHttpCodeEnum.NEED_LOGIN);
        }

        //查询作者信息
        ApAuthor apAuthor = apAuthorFeign.findOne(dto.getAuthorId()).getData();
        if(apAuthor==null){
            throw new LeadNewsException(AppHttpCodeEnum.DATA_NOT_EXIST);
        }

        //查询用户信息
        apUser = apUserMapper.selectById(apUser.getId());

        try {
            Integer authorUserId = apAuthor.getUserId();
            Integer userId = apUser.getId();

            QueryWrapper<ApUserFollow> followQueryWrapper = new QueryWrapper<>();
            followQueryWrapper.eq("user_id",userId);
            followQueryWrapper.eq("follow_id",authorUserId);

            //先查询用户粉丝表是否有记录
            QueryWrapper<ApUserFan> fanQueryWrapper = new QueryWrapper<>();
            fanQueryWrapper.eq("user_id",authorUserId);
            fanQueryWrapper.eq("fans_id",userId);
            ApUserFan userFan = apUserFanMapper.selectOne(fanQueryWrapper);

            //2.判断如果是关注
            if(dto.getOperation().equals(0)){
                //2.1 先查询用户关注表是否有记录
                ApUserFollow userFollow = getOne(followQueryWrapper);
                if(userFollow==null){
                    //2.2 没有记录才添加用户关注记录
                    userFollow = new ApUserFollow();
                    userFollow.setUserId(userId);
                    userFollow.setFollowId(authorUserId);
                    userFollow.setFollowName(apAuthor.getName());
                    userFollow.setLevel(1);
                    userFollow.setIsNotice(1);
                    userFollow.setCreatedTime(new Date());
                    save(userFollow);
                }

                if(userFan==null){
                    //2.4 没有记录才添加用户粉丝记录
                    userFan = new ApUserFan();
                    userFan.setUserId(authorUserId);
                    userFan.setFansId(userId);
                    userFan.setFansName(apUser.getName());
                    userFan.setLevel(0);
                    userFan.setCreatedTime(new Date());
                    userFan.setIsDisplay(1);
                    userFan.setIsShieldComment(0);
                    userFan.setIsShieldLetter(0);
                    apUserFanMapper.insert(userFan);
                }


                //同步记录关注行为数据
                //需要的数据： 用户ID  文章ID  关注作者的用户ID
                FollowBehaviorDto followBehaviorDto = new FollowBehaviorDto();
                followBehaviorDto.setArticleId(dto.getArticleId());
                followBehaviorDto.setFollowId(authorUserId);
                followBehaviorDto.setUserId(userId);

                kafkaTemplate.send(MQConstants.FOLLOW_BEHAVIOR_TOPIC, JsonUtils.toString(followBehaviorDto));

            }else{
                //3.如果是取消关注
                //3.1 删除用户关注记录
                remove(followQueryWrapper);
                //3.2 删除用户粉丝记录
                apUserFanMapper.delete(fanQueryWrapper);
            }
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            throw new LeadNewsException(AppHttpCodeEnum.SERVER_ERROR);
        }
    }

    @Override
    public ApUserFollow findUserFollow(Integer userId, Integer followId) {
        QueryWrapper<ApUserFollow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("follow_id",followId);
        return getOne(queryWrapper);
    }
}

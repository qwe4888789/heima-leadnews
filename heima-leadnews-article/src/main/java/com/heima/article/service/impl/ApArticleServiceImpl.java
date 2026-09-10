package com.heima.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.article.mapper.ApArticleConfigMapper;
import com.heima.article.mapper.ApArticleContentMapper;
import com.heima.article.mapper.ApArticleMapper;
import com.heima.article.mapper.ApAuthorMapper;
import com.heima.article.service.ApArticleService;
import com.heima.article.service.ApCollectionService;
import com.heima.behavior.feign.ApBehaviorEntryFeign;
import com.heima.behavior.feign.ApLikesBehaviorFeign;
import com.heima.behavior.feign.ApUnlikesBehaviorFeign;
import com.heima.common.dtos.Result;
import com.heima.common.exception.AppHttpCodeEnum;
import com.heima.common.exception.LeadNewsException;
import com.heima.model.article.dtos.ApArticleDto;
import com.heima.model.article.dtos.ApArticleInfoDto;
import com.heima.model.article.dtos.ArticleBehaviorDto;
import com.heima.model.article.pojos.*;
import com.heima.model.behavior.pojos.ApBehaviorEntry;
import com.heima.model.behavior.pojos.ApLikesBehavior;
import com.heima.model.behavior.pojos.ApUnlikesBehavior;
import com.heima.model.user.pojos.ApUser;
import com.heima.model.user.pojos.ApUserFollow;
import com.heima.user.feign.ApUserFollowFeign;
import com.heima.utils.common.ThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文章信息表，存储已发布的文章 服务实现类
 * </p>
 *
 * @author heima
 * @since 2021-07-22
 */
@Service
@Transactional
public class ApArticleServiceImpl extends ServiceImpl<ApArticleMapper, ApArticle> implements ApArticleService {
    @Autowired
    private ApArticleMapper apArticleMapper;
    @Autowired
    private ApArticleContentMapper apArticleContentMapper;
    @Autowired
    private ApArticleConfigMapper apArticleConfigMapper;
    @Autowired
    private ApBehaviorEntryFeign apBehaviorEntryFeign;
    @Autowired
    private ApLikesBehaviorFeign apLikesBehaviorFeign;
    @Autowired
    private ApUnlikesBehaviorFeign apUnlikesBehaviorFeign;
    @Autowired
    private ApUserFollowFeign apUserFollowFeign;
    @Autowired
    private ApCollectionService apCollectionService;
    @Autowired
    private ApAuthorMapper apAuthorMapper;
    @Override
    public Result<List<ApArticle>> loadApArticle(ApArticleDto dto, int type) {
        try {
            //1.初始值设置
            if(dto.getMinBehotTime()==null)dto.setMinBehotTime(new Date());
            if(dto.getMaxBehotTime()==null)dto.setMaxBehotTime(new Date());
            if(dto.getSize()==null)dto.setSize(10);

            return  Result.ok(apArticleMapper.loadApArticle(dto,type));
        } catch (Exception e) {
            e.printStackTrace();
            throw new LeadNewsException(AppHttpCodeEnum.SERVER_ERROR);
        }
    }

    @Override
    public Result<Map<String, Object>> loadArticleInfo(ApArticleInfoDto dto) {
        //1.判断文章是否存在
        ApArticle apArticle = apArticleMapper.selectById(dto.getArticleId());
        if(apArticle==null){
            throw new LeadNewsException(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        QueryWrapper<ApArticleContent> contentQueryWrapper = new QueryWrapper<>();
        contentQueryWrapper.eq("article_id",dto.getArticleId());
        ApArticleContent apArticleContent = apArticleContentMapper.selectOne(contentQueryWrapper);
        if(apArticleContent==null){
            throw new LeadNewsException(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        QueryWrapper<ApArticleConfig> configQueryWrapper = new QueryWrapper<>();
        configQueryWrapper.eq("article_id",dto.getArticleId());
        ApArticleConfig apArticleConfig = apArticleConfigMapper.selectOne(configQueryWrapper);
        if(apArticleConfig==null){
            throw new LeadNewsException(AppHttpCodeEnum.DATA_NOT_EXIST);
        }

        //2.判断该文章是否被删除或下架
        if(apArticleConfig.getIsDelete().equals(1) || apArticleConfig.getIsDown().equals(1)){
            throw new LeadNewsException(404,"该文章已被移除或下架");
        }

        //3.返回数据
        Map<String, Object> map = new HashMap<>();
        map.put("content",apArticleContent);
        map.put("config",apArticleConfig);

        return Result.ok(map);
    }

    @Override
    public Result<Map<String, Boolean>> loadArticleBehavior(ArticleBehaviorDto dto) {

        //1.查询登录用户信息
        ApUser apUser = (ApUser)ThreadLocalUtils.get();
        Integer userId = null;
        if(apUser!=null){
            userId = apUser.getId();
        }

        //2.使用用户ID和设备ID查询行为实体
        ApBehaviorEntry behaviorEntry = apBehaviorEntryFeign.findByUserIdOrEquipmentId(userId, dto.getEquipmentId());
        if(behaviorEntry==null)return Result.ok();

        boolean islike = false,isunlike = false,iscollection=false,isfollow=false;

        //3.查询点赞行为数据
        ApLikesBehavior likesBehavior = apLikesBehaviorFeign.findLike(behaviorEntry.getId(), dto.getArticleId());
        if(likesBehavior!=null && likesBehavior.getOperation().equals(0)){
            islike=true;
        }

        //4.查询不喜欢行为数据
        ApUnlikesBehavior unlikesBehavior = apUnlikesBehaviorFeign.findUnlike(behaviorEntry.getId(), dto.getArticleId());
        if(unlikesBehavior!=null && unlikesBehavior.getType().equals(0)){
            isunlike=true;
        }

        //5.查询收藏行为数据
        ApCollection apCollection = apCollectionService.findCollection(behaviorEntry.getId(), dto.getArticleId());
        if(apCollection!=null){
            iscollection=true;
        }

        //6.查询用户关注数据(前提：必须是登录用户)
        if(apUser!=null){
            ApAuthor apAuthor = apAuthorMapper.selectById(dto.getAuthorId());
            if(apAuthor!=null){
                ApUserFollow userFollow = apUserFollowFeign.findUserFollow(userId, apAuthor.getUserId());
                if(userFollow!=null){
                    isfollow = true;
                }
            }
        }

        //7.封装Map集合返回
        Map<String,Boolean> map = new HashMap<>();
        map.put("islike",islike);
        map.put("isunlike",isunlike);
        map.put("iscollection",iscollection);
        map.put("isfollow",isfollow);

        return Result.ok(map);
    }
}

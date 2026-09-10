package com.heima.comment.service.impl;

import com.heima.comment.service.ApCommentReplyService;
import com.heima.common.dtos.Result;
import com.heima.common.exception.AppHttpCodeEnum;
import com.heima.common.exception.LeadNewsException;
import com.heima.model.comment.dtos.*;
import com.heima.model.comment.pojos.ApComment;
import com.heima.model.comment.pojos.ApCommentReply;
import com.heima.model.comment.pojos.ApCommentReplyLike;
import com.heima.model.user.pojos.ApUser;
import com.heima.user.feign.ApUserFeign;
import com.heima.utils.common.BeanHelper;
import com.heima.utils.common.ThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ApCommentReplyServiceImpl implements ApCommentReplyService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private ApUserFeign apUserFeign;


    @Override
    public Result saveCommentReply(CommentReplySaveDto dto) {
        //1.判断用户是否登录（登录才可以评论）
        ApUser apUser = (ApUser) ThreadLocalUtils.get();
        if(apUser==null){
            throw new LeadNewsException(AppHttpCodeEnum.NEED_LOGIN);
        }

        //2.判断内容长度不超过140
        if(dto.getContent().length()>140){
            throw new LeadNewsException(500,"评论长度不超过140个字符");
        }

        //3.查询用户数据
        apUser = apUserFeign.findOne(apUser.getId()).getData();

        try {
            //2.保存评论回复
            ApCommentReply apCommentReply = new ApCommentReply();
            apCommentReply.setContent(dto.getContent());
            if(apUser!=null){
                apCommentReply.setAuthorId(apUser.getId());
                apCommentReply.setAuthorName(apUser.getName());
            }
            apCommentReply.setCommentId(dto.getCommentId());
            apCommentReply.setLikes(0);//点赞数
            apCommentReply.setCreatedTime(new Date());
            mongoTemplate.insert(apCommentReply);

            //添加了回复后，评论的回复数+1
            Query query = Query.query(Criteria.where("_id").is(dto.getCommentId()));
            Update update = new Update();
            update.inc("reply");
            mongoTemplate.updateMulti(query, update, ApComment.class);

            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            throw new LeadNewsException(AppHttpCodeEnum.SERVER_ERROR);
        }
    }

    @Override
    public Result<Map<String, Integer>> likeCommentReply(CommentReplyLikeDto dto) {
        //1.判断用户是否登录
        ApUser apUser = (ApUser)ThreadLocalUtils.get();
        if(apUser==null){
            throw new LeadNewsException(AppHttpCodeEnum.NEED_LOGIN);
        }

        //2.判断操作类型，根据进行点赞/取消点赞
        //查询用户是否点赞过
        Query likeQuery = Query.query(Criteria.where("authorId").is(apUser.getId()).and("commentReplyId").is(dto.getCommentReplyId()));
        ApCommentReplyLike commentReplyLike = mongoTemplate.findOne(likeQuery, ApCommentReplyLike.class);

        if(commentReplyLike==null && dto.getOperation().equals(0)){ //只有用户没有点赞过，才可以点赞
            //点赞
            //保存回复点赞记录
            ApCommentReplyLike apCommentReplyLike = new ApCommentReplyLike();
            apCommentReplyLike.setAuthorId(apUser.getId());
            apCommentReplyLike.setCommentReplyId(dto.getCommentReplyId());
            mongoTemplate.insert(apCommentReplyLike);

            //更新评论回复的点赞数+1
            Query query = Query.query(Criteria.where("_id").is(dto.getCommentReplyId()));
            Update update = new Update();
            update.inc("likes");
            mongoTemplate.updateMulti(query,update,ApCommentReply.class);

        }else{
            //取消点赞
            //删除回复点赞记录
            Query query = Query.query(Criteria.where("commentReplyId").is(dto.getCommentReplyId()).and("authorId").is(apUser.getId()));
            mongoTemplate.remove(query, ApCommentReplyLike.class);

            //更新评论回复的点赞数-1
            Query commentQuery =  Query.query(Criteria.where("_id").is(dto.getCommentReplyId()));
            Update update = new Update();
            update.inc("likes",-1);
            mongoTemplate.updateMulti(commentQuery,update,ApCommentReply.class);
        }

        //3.查询最新的评论回复点赞数并返回
        ApCommentReply apCommentReply = mongoTemplate.findById(dto.getCommentReplyId(), ApCommentReply.class);
        Map<String,Integer> map = new HashMap<>();
        map.put("likes",apCommentReply.getLikes());
        return Result.ok(map);
    }

    @Override
    public Result<List> loadCommentReply(CommentReplyDto dto) {
        //设置初始值
        if(dto.getMinDate()==null){
            dto.setMinDate(new Date());
        }

        //1.根据页面的条件查询所需要的数据
        Query query = Query.query(Criteria.where("commentId").is(dto.getCommentId()).and("createdTime").lt(dto.getMinDate()));
        //分页
        query.limit(10);
        //排序
        query.with(Sort.by(Sort.Direction.DESC,"createdTime"));
        List<ApCommentReply> apCommentReplyList = mongoTemplate.find(query, ApCommentReply.class);

        //2.如果用户没有登录，直接返回查询的数据
        ApUser apUser = (ApUser)ThreadLocalUtils.get();
        if(apUser==null){
            return Result.ok(apCommentReplyList);
        }

        //3.如果用户登录，查询当前用户对上面的评论的点回复赞记录，这些评论封装成ApCommentVo（operation=0）对象返回

        //查询当前用户对上面的评论的点赞记录
        List<String> commentIdList = apCommentReplyList.stream().map(ApCommentReply::getId).collect(Collectors.toList());
        Query likeQuery = Query.query(Criteria.where("authorId").is(apUser.getId()).and("commentReplyId").in(commentIdList));
        List<ApCommentReplyLike> apCommentReplyLikes = mongoTemplate.find(likeQuery, ApCommentReplyLike.class);

        List<ApCommentReplyVo> apCommentReplyVoList = new ArrayList<>();
        //遍历，封装ApCommentReplyVo
        if(apCommentReplyList!=null){
            apCommentReplyList.forEach(apCommentReply -> {
                ApCommentReplyVo apCommentReplyVo = BeanHelper.copyProperties(apCommentReply,ApCommentReplyVo.class);

                long count = apCommentReplyLikes.stream().filter(like -> like.getCommentReplyId().equals(apCommentReply.getId())).count();
                if(count>0){
                    apCommentReplyVo.setOperation(0);
                }

                apCommentReplyVoList.add(apCommentReplyVo);
            });
        }
        return Result.ok(apCommentReplyVoList);
    }
}

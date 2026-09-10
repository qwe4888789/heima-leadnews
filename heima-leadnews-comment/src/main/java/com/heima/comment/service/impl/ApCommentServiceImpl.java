package com.heima.comment.service.impl;

import com.heima.comment.service.ApCommentService;
import com.heima.common.dtos.Result;
import com.heima.common.exception.AppHttpCodeEnum;
import com.heima.common.exception.LeadNewsException;
import com.heima.model.comment.dtos.ApCommentVo;
import com.heima.model.comment.dtos.CommentDto;
import com.heima.model.comment.dtos.CommentLikeDto;
import com.heima.model.comment.dtos.CommentSaveDto;
import com.heima.model.comment.pojos.ApComment;
import com.heima.model.comment.pojos.ApCommentLike;
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
public class ApCommentServiceImpl implements ApCommentService {

    @Autowired
    private ApUserFeign apUserFeign;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Result saveComment(CommentSaveDto dto) {
        //1.判断用户是否登录（登录才可以评论）
        ApUser apUser = (ApUser)ThreadLocalUtils.get();
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
            //2.保存评论
            ApComment apComment = new ApComment();
            apComment.setContent(dto.getContent());
            if(apUser!=null){
                apComment.setAuthorId(apUser.getId());
                apComment.setAuthorName(apUser.getName());
                apComment.setImage(apUser.getImage());
            }
            apComment.setEntryId(dto.getArticleId());
            apComment.setType(0);
            apComment.setLikes(0);//点赞数
            apComment.setReply(0);//回复数
            apComment.setFlag(0);
            apComment.setCreatedTime(new Date());
            mongoTemplate.insert(apComment);

            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            throw new LeadNewsException(AppHttpCodeEnum.SERVER_ERROR);
        }
    }

    @Override
    public Result<Map<String, Integer>> likeComment(CommentLikeDto dto) {
        //1.判断用户是否登录
        ApUser apUser = (ApUser)ThreadLocalUtils.get();
        if(apUser==null){
            throw new LeadNewsException(AppHttpCodeEnum.NEED_LOGIN);
        }

        //2.判断操作类型，根据进行点赞/取消点赞
        //查询用户是否点赞过
        Query likeQuery = Query.query(Criteria.where("authorId").is(apUser.getId()).and("commentId").is(dto.getCommentId()));
        ApCommentLike commentLike = mongoTemplate.findOne(likeQuery, ApCommentLike.class);

        if(commentLike==null && dto.getOperation().equals(0)){ //只有用户没有点赞过，才可以点赞
            //点赞
            //保存点赞记录
            ApCommentLike apCommentLike = new ApCommentLike();
            apCommentLike.setAuthorId(apUser.getId());
            apCommentLike.setCommentId(dto.getCommentId());
            mongoTemplate.insert(apCommentLike);

            //更新评论的点赞数+1
            Query query = Query.query(Criteria.where("_id").is(dto.getCommentId()));
            Update update = new Update();
            update.inc("likes");
            mongoTemplate.updateMulti(query,update,ApComment.class);

        }else{
            //取消点赞
            //删除点赞记录
            Query query = Query.query(Criteria.where("commentId").is(dto.getCommentId()).and("authorId").is(apUser.getId()));
            mongoTemplate.remove(query,ApCommentLike.class);

            //更新评论的点赞数-1
            Query commentQuery =  Query.query(Criteria.where("_id").is(dto.getCommentId()));
            Update update = new Update();
            update.inc("likes",-1);
            mongoTemplate.updateMulti(commentQuery,update,ApComment.class);
        }

        //3.查询最新的评论点赞数并返回
        ApComment apComment = mongoTemplate.findById(dto.getCommentId(), ApComment.class);
        Map<String,Integer> map = new HashMap<>();
        map.put("likes",apComment.getLikes());
        return Result.ok(map);
    }

    @Override
    public Result<List> loadComment(CommentDto dto) {

        //1.根据页面的条件查询所需要的数据
        Query query = Query.query(Criteria.where("entryId").is(dto.getArticleId()).and("createdTime").lt(dto.getMinDate()));
        //分页
        query.limit(10);
        //排序
        query.with(Sort.by(Sort.Direction.DESC,"createdTime"));
        List<ApComment> apCommentList = mongoTemplate.find(query, ApComment.class);

        //2.如果用户没有登录，直接返回查询的数据
        ApUser apUser = (ApUser)ThreadLocalUtils.get();
        if(apUser==null){
            return Result.ok(apCommentList);
        }

        //3.如果用户登录，查询当前用户对上面的评论的点赞记录，这些评论封装成ApCommentVo（operation=0）对象返回

        //查询当前用户对上面的评论的点赞记录
        List<String> commentIdList = apCommentList.stream().map(ApComment::getId).collect(Collectors.toList());
        Query likeQuery = Query.query(Criteria.where("authorId").is(apUser.getId()).and("commentId").in(commentIdList));
        List<ApCommentLike> apCommentLikes = mongoTemplate.find(likeQuery, ApCommentLike.class);

        List<ApCommentVo> apCommentVoList = new ArrayList<>();
        //遍历，封装ApCommentVo
        if(apCommentList!=null){
            apCommentList.forEach(apComment -> {
                ApCommentVo apCommentVo = BeanHelper.copyProperties(apComment,ApCommentVo.class);

                long count = apCommentLikes.stream().filter(like -> like.getCommentId().equals(apComment.getId())).count();
                if(count>0){
                    apCommentVo.setOperation(0);
                }

                apCommentVoList.add(apCommentVo);
            });
        }
        return Result.ok(apCommentVoList);
    }
}

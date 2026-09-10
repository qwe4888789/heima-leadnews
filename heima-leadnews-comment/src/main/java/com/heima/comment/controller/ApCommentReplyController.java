package com.heima.comment.controller;

import com.heima.comment.service.ApCommentReplyService;
import com.heima.common.dtos.Result;
import com.heima.model.comment.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 评论回复
 */
@RestController
@RequestMapping("/api/apCommentReply")
public class ApCommentReplyController {
    @Autowired
    private ApCommentReplyService apCommentReplyService;

    /**
     * 发表评论回复
     */
    @PostMapping("/save")
    public Result saveCommentReply(@RequestBody CommentReplySaveDto dto){
        return apCommentReplyService.saveCommentReply(dto);
    }

    /**
     * 评论回复点赞
     */
    @PostMapping("/like")
    public Result<Map<String,Integer>> likeCommentReply(@RequestBody CommentReplyLikeDto dto){
        return apCommentReplyService.likeCommentReply(dto);
    }

    /**
     * 评论回复列表
     */
    @PostMapping("/load")
    public Result<List> loadCommentReply(@RequestBody CommentReplyDto dto){
        return apCommentReplyService.loadCommentReply(dto);
    }
}

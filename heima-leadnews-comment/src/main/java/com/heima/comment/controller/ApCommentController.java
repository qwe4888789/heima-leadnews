package com.heima.comment.controller;

import com.heima.comment.service.ApCommentService;
import com.heima.common.dtos.Result;
import com.heima.model.comment.dtos.CommentDto;
import com.heima.model.comment.dtos.CommentLikeDto;
import com.heima.model.comment.dtos.CommentSaveDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 评论
 */
@RestController
@RequestMapping("/api/apComment")
public class ApCommentController {
    @Autowired
    private ApCommentService apCommentService;

    /**
     * 发表评论
     */
    @PostMapping("/save")
    public Result saveComment(@RequestBody CommentSaveDto dto){
        return apCommentService.saveComment(dto);
    }

    /**
     * 评论点赞
     */
    @PostMapping("/like")
    public Result<Map<String,Integer>> likeComment(@RequestBody CommentLikeDto dto){
        return apCommentService.likeComment(dto);
    }

    /**
     * 评论列表
     */
    @PostMapping("/load")
    public Result<List> loadComment(@RequestBody CommentDto dto){
        return apCommentService.loadComment(dto);
    }
}

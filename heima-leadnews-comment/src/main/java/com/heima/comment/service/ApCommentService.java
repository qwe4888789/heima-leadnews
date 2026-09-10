package com.heima.comment.service;

import com.heima.common.dtos.Result;
import com.heima.model.comment.dtos.CommentDto;
import com.heima.model.comment.dtos.CommentLikeDto;
import com.heima.model.comment.dtos.CommentSaveDto;

import java.util.List;
import java.util.Map;

public interface ApCommentService {
    public Result saveComment(CommentSaveDto dto);

    Result<Map<String, Integer>> likeComment(CommentLikeDto dto);

    Result<List> loadComment(CommentDto dto);
}

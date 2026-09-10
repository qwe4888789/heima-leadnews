package com.heima.comment.service;

import com.heima.common.dtos.Result;
import com.heima.model.comment.dtos.CommentReplyDto;
import com.heima.model.comment.dtos.CommentReplyLikeDto;
import com.heima.model.comment.dtos.CommentReplySaveDto;

import java.util.List;
import java.util.Map;

public interface ApCommentReplyService {
    Result saveCommentReply(CommentReplySaveDto dto);

    Result<Map<String, Integer>> likeCommentReply(CommentReplyLikeDto dto);

    Result<List> loadCommentReply(CommentReplyDto dto);
}

package com.heima.model.comment.dtos;

import com.heima.model.comment.pojos.ApCommentReply;
import lombok.Data;

@Data
public class ApCommentReplyVo extends ApCommentReply {

    /**
     * 0：点赞
     * 1：取消点赞
     */
    private Integer operation;
}
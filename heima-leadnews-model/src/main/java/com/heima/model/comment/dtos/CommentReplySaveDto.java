package com.heima.model.comment.dtos;

import lombok.Data;

@Data
public class CommentReplySaveDto {

    /**
     * 评论id
     */
    private String commentId;

    /**
     * 回复内容
     */
    private String content;
}
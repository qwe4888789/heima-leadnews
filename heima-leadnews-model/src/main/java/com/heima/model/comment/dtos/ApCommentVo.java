package com.heima.model.comment.dtos;

import com.heima.model.comment.pojos.ApComment;
import lombok.Data;

@Data
public class ApCommentVo extends ApComment {
    private Integer operation;
}

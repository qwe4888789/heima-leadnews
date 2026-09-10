package com.heima.model.user.dtos;

import lombok.Data;

/**
 * 接收用户认证审核参数
 */
@Data
public class AuthDTO {
    private Integer id;//认证用户ID
    private String msg;//驳回信息
}
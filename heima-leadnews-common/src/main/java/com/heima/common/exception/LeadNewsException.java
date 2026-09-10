package com.heima.common.exception;

import lombok.Data;

/**
 * 业务异常类
 */
@Data
public class LeadNewsException extends RuntimeException{
    private Integer status;//状态码

    public LeadNewsException(Integer status,String message){
        super(message);
        this.status = status;
    }

    public LeadNewsException(AppHttpCodeEnum appHttpCodeEnum){
        super(appHttpCodeEnum.getMessage());
        this.status = appHttpCodeEnum.getCode();
    }
}

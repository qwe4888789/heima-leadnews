package com.heima.model.user.dtos;

/**
 * 用户认证状态码枚举类
 */
public enum  AppUserStatusEnum {
    AUTH_ING(0,"创建中"),
    AUTH_WAITING(1,"待审核"),
    AUTH_FAIL(2,"审核失败"),
    AUTH_SUCCESS(9,"审核成功");


    private Integer value;
    private String msg;

    AppUserStatusEnum(Integer value,String msg){
        this.value = value;
        this.msg = msg;
    }

    public Integer getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }
}
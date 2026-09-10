package com.heima.model.user.dtos;

public enum WmUserStatusEnum {
    OK(9,"有效"),
    LOCKED(0,"冻结"),
    INVALID(1,"永久失效");


    private Integer value;
    private String msg;

    WmUserStatusEnum(Integer value,String msg){
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
package com.heima.model.user.dtos;

public enum  ApAuthorTypeEnum {
    MEDIA_USER(2,"平台媒体人"),
    MEDIA_SELLER(1,"合作商"),
    MEDIA_AUTHRO(0,"普通作者");


    private Integer value;
    private String msg;

    ApAuthorTypeEnum(Integer value,String msg){
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
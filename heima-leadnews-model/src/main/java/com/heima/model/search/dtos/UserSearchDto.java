package com.heima.model.search.dtos;

import lombok.Data;

@Data
public class UserSearchDto {

    // 设备ID
    Integer equipmentId;
    /**
    * 搜索关键字
    */
    String searchWords;
    /**
    * 当前页
    */
    int pageNum;
    /**
    * 分页条数
    */
    int pageSize;

    Integer id;//接受删除请求

}
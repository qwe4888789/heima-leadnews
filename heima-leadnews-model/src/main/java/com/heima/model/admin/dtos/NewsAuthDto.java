package com.heima.model.admin.dtos;

import lombok.Data;

@Data
public class NewsAuthDto  {

    /**
     * 文章标题
     */
    private String title;

    private Integer id;
    
    /**
     * 失败原因
     */
    private String msg;
}
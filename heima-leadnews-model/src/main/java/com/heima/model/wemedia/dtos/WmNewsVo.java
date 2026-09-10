package com.heima.model.wemedia.dtos;

import com.heima.model.wemedia.pojos.WmNews;
import lombok.Data;

@Data
public class WmNewsVo extends WmNews {
    //作者名称
    private String authorName;
}
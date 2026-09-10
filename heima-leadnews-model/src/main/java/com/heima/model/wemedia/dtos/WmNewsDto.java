package com.heima.model.wemedia.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heima.model.wemedia.pojos.WmNews;
import lombok.Data;

import java.util.Date;

@Data
public class WmNewsDto extends WmNews {
    private String keyword;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date beginPubDate;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date endPubDate;
}

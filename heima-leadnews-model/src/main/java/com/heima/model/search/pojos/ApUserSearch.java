package com.heima.model.search.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("ap_user_search")
public class ApUserSearch {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("entry_id")
    private Integer entryId;

    @TableField("keyword")
    private String keyword;

    @TableField("status")
    private Boolean status;

    @TableField("created_time")
    private Date createdTime;
}

package com.heima.model.admin.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 频道信息表
 * </p>
 *
 * @author heima
 * @since 2021-07-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ad_channel")
@ApiModel(value="AdChannel", description="频道信息表")
public class AdChannel implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "频道名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "频道描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "是否默认频道")
    @TableField("is_default")
    private Boolean isDefault;

    @TableField("status")
    private Boolean status;

    @ApiModelProperty(value = "默认排序")
    @TableField("ord")
    private Integer ord;

    @ApiModelProperty(value = "创建时间")
    @TableField("created_time")
    private Date createdTime;


}

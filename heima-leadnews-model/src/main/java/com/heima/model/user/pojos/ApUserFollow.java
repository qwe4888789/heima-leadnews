package com.heima.model.user.pojos;

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
 * APP用户关注信息表
 * </p>
 *
 * @author heima
 * @since 2021-07-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ap_user_follow")
@ApiModel(value="ApUserFollow", description="APP用户关注信息表")
public class ApUserFollow implements Serializable {


    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户ID")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "关注作者ID")
    @TableField("follow_id")
    private Integer followId;

    @ApiModelProperty(value = "粉丝昵称")
    @TableField("follow_name")
    private String followName;

    @ApiModelProperty(value = "关注度	            0 偶尔感兴趣	            1 一般	            2 经常	            3 高度")
    @TableField("level")
    private Integer level;

    @ApiModelProperty(value = "是否动态通知")
    @TableField("is_notice")
    private Integer isNotice;

    @ApiModelProperty(value = "创建时间")
    @TableField("created_time")
    private Date createdTime;


}

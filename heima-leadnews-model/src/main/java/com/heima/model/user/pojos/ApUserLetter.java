package com.heima.model.user.pojos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * APP用户私信信息表
 * </p>
 *
 * @author heima
 * @since 2021-07-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ap_user_letter")
@ApiModel(value="ApUserLetter", description="APP用户私信信息表")
public class ApUserLetter implements Serializable {


    @ApiModelProperty(value = "主键")
    @TableField("id")
    private Integer id;

    @ApiModelProperty(value = "用户ID")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "发送人ID")
    @TableField("sender_id")
    private Integer senderId;

    @ApiModelProperty(value = "发送人昵称")
    @TableField("sender_name")
    private String senderName;

    @ApiModelProperty(value = "私信内容")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "是否阅读")
    @TableField("is_read")
    private Integer isRead;

    @ApiModelProperty(value = "创建时间")
    @TableField("created_time")
    private Date createdTime;

    @ApiModelProperty(value = "阅读时间")
    @TableField("read_time")
    private Date readTime;


}

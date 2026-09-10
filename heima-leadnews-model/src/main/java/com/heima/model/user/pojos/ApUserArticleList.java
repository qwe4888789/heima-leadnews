package com.heima.model.user.pojos;

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
 * APP用户文章列表
 * </p>
 *
 * @author heima
 * @since 2021-07-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ap_user_article_list")
@ApiModel(value="ApUserArticleList", description="APP用户文章列表")
public class ApUserArticleList implements Serializable {


    @ApiModelProperty(value = "主键")
    @TableId("id")
    private Integer id;

    @ApiModelProperty(value = "用户ID")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "频道ID")
    @TableField("channel_id")
    private Integer channelId;

    @ApiModelProperty(value = "动态ID")
    @TableField("article_id")
    private Integer articleId;

    @ApiModelProperty(value = "是否展示")
    @TableField("is_show")
    private Integer isShow;

    @ApiModelProperty(value = "推荐时间")
    @TableField("recommend_time")
    private Date recommendTime;

    @ApiModelProperty(value = "是否阅读")
    @TableField("is_read")
    private Integer isRead;

    @ApiModelProperty(value = "推荐算法")
    @TableField("strategy_id")
    private Integer strategyId;


}

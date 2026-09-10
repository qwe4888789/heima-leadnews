package com.heima.model.wemedia.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 自媒体图文内容信息表
 * </p>
 *
 * @author heima
 * @since 2021-07-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wm_news")
@ApiModel(value="WmNews", description="自媒体图文内容信息表")
public class WmNews implements Serializable {


    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "自媒体用户ID")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "标题")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "图文内容")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "文章布局	            0 无图文章	            1 单图文章	            3 多图文章")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "图文频道ID")
    @TableField("channel_id")
    private Integer channelId;

    @TableField("labels")
    private String labels;

    @ApiModelProperty(value = "创建时间")
    @TableField("created_time")
    private Date createdTime;

    @ApiModelProperty(value = "提交时间")
    @TableField("submited_time")
    private Date submitedTime;

    @ApiModelProperty(value = "当前状态	            0 草稿	            1 提交（待审核）	            2 审核失败	            3 人工审核	            4 人工审核通过	            8 审核通过（待发布）	            9 已发布")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "定时发布时间，不定时则为空")
    @TableField("publish_time")
    private Date publishTime;

    @ApiModelProperty(value = "拒绝理由")
    @TableField("reason")
    private String reason;

    @ApiModelProperty(value = "发布库文章ID")
    @TableField("article_id")
    private Long articleId;

    @ApiModelProperty(value = "//图片用逗号分隔")
    @TableField("images")
    private String images;

    @TableField("enable")
    private Integer enable;

    //状态枚举类
    @Alias("WmNewsStatus")
    public enum Status{
        NORMAL(0),SUBMIT(1),FAIL(2),ADMIN_AUTH(3),ADMIN_SUCCESS(4),SUCCESS(8),PUBLISHED(9);
        Integer code;
        Status(Integer code){
            this.code = code;
        }
        public Integer getCode(){
            return this.code;
        }
    }
}

package com.heima.model.search.pojos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.heima.model.common.Long2StringSerializer;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Data
@Document(indexName = "article")   //indexName: 索引库名称
public class ArticleDocument {

    @Id   //主键
    @JsonSerialize(using = Long2StringSerializer.class)//需要将long类型的数据转成字符串，因为es中存储的都是字符串
    private Long id;

    @Field(type = FieldType.Text,analyzer = "ik_smart")
    private String title;

    private Integer authorId;

    private String authorName;

    private Integer channelId;

    private String channelName;

    private Integer layout;

    private String images;

    private Integer likes;

    private Integer collection;

    private Integer comment;

    private Integer views;

    private Date createdTime;

    private Date publishTime;

}
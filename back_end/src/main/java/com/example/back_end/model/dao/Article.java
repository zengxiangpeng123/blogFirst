package com.example.back_end.model.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 文章表
 * @TableName article
 */
@TableName(value ="article")
@Data
public class Article {
    /**
     * 文章ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 作者ID
     */
    private Long userId;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 标题
     */
    private String title;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 内容
     */
    private String content;

    /**
     * 封面图
     */
    private String cover;

    /**
     * 标签(逗号分隔)
     */
    private String tags;

    /**
     * 浏览量
     */
    private Integer viewCount;

    /**
     * 是否置顶
     */
    private Integer isPinned;

    /**
     * 是否发布
     */
    private Integer isPublished;

    /**
     * 
     */
    private Date createdAt;

    /**
     * 
     */
    private Date updatedAt;
}
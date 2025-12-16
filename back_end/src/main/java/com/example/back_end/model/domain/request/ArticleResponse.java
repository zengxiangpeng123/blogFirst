package com.example.back_end.model.domain.request;

import lombok.Data;

import java.util.Date;

/**
 * 文章响应VO（包含分类名称和作者名称）
 */
@Data
public class ArticleResponse {
    private Long id;
    private Long userId;
    private Long categoryId;
    private String title;
    private String summary;
    private String content;
    private String cover;
    private String tags;
    private Integer viewCount;
    private Integer isPinned;
    private Integer isPublished;
    private Date createdAt;
    private Date updatedAt;
    
    // 关联字段
    private String categoryName;  // 分类名称
    private String authorName;    // 作者名称（用户昵称或用户名）
}

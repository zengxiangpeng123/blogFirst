package com.example.back_end.model.domain.request;

import lombok.Data;

@Data
public class ArticleQueryRequest {
    private Long userId;
    private Long categoryId;
    private Integer isPinned;
    private Integer isPublished;
//    private String keyword;  // 关键词搜索
    private Integer pageNum = 1;
    private Integer pageSize = 10;
}

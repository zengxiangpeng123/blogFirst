package com.example.back_end.model.domain.request;

import lombok.Data;

@Data
public class ArticleQueryRequest {
    private Long userId;
    private Long categoryId;
    private Integer isPinned;
    private Integer isPublished;
    private String keyword;  // 关键词搜索（标题模糊匹配）
    private Integer pageNum = 1;
    private Integer pageSize = 10;

    //将对应的实体类属性名和请求参数名进行映射
    private String categoryName;
    private String username;

}

package com.example.back_end.model.domain.response;

import lombok.Data;
import java.util.Date;

/**
 * 收藏响应VO（包含文章信息）
 */
@Data
public class FavoriteResponse {
    private Long id;           // 收藏记录ID
    private Long articleId;    // 文章ID
    private String title;      // 文章标题
    private String categoryName; // 分类名称
    private Date createdAt;    // 收藏时间
}

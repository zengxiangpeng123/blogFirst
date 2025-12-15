package com.example.back_end.model.domain.request;

import lombok.Data;

@Data
public class FavoriteQueryRequest {
    private Long userId;
    private Long categoryId;
    private Integer pageNum = 1;
    private Integer pageSize = 10;
}

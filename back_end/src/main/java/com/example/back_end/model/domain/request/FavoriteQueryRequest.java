package com.example.back_end.model.domain.request;

import lombok.Data;

@Data
public class FavoriteQueryRequest {
    private Long userId;
    private Long articleId;
    //点击的状态
    private int isExist;

}

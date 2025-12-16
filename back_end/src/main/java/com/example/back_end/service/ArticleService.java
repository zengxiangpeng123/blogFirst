package com.example.back_end.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.back_end.model.domain.Article;
import com.example.back_end.model.domain.request.ArticleQueryRequest;
import com.example.back_end.model.domain.request.ArticleResponse;

/**
 * @author 15173
 * @description 针对表【article(文章表)】的数据库操作Service
 * @createDate 2025-12-12 15:15:21
 */
public interface ArticleService extends IService<Article> {
    
    /**
     * 分页查询文章（带分类名称和作者名称）
     */
    Page<ArticleResponse> getArticlePage(ArticleQueryRequest request);
}

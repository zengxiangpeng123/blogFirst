package com.example.back_end.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.back_end.model.domain.Article;
import com.example.back_end.model.domain.request.ArticleResponse;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author 15173
 * @description 针对表【article(文章表)】的数据库操作Mapper
 * @createDate 2025-12-12 15:15:21
 */
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 分页查询文章（带分类名称和作者名称）
     */
    Page<ArticleResponse> selectArticlePage(
            Page<ArticleResponse> page,
            @Param("userId") Long userId,
            @Param("categoryId") Long categoryId,
            @Param("isPinned") Integer isPinned,
            @Param("isPublished") Integer isPublished,
            @Param("keyword") String keyword
    );
}

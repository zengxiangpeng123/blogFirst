package com.example.back_end.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.back_end.mapper.ArticleMapper;
import com.example.back_end.model.domain.Article;
import com.example.back_end.model.domain.request.ArticleQueryRequest;
import com.example.back_end.service.ArticleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author 15173
 * @description 针对表【article(文章表)】的数据库操作Service实现
 * @createDate 2025-12-12 15:15:21
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
        implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    /**
     * 获得文章分页列表
     * @param request 文章查询请求
     * @return 文章分页列表
     */
    @Override
    public Page<Article> getArticlePage(ArticleQueryRequest request) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();

        // 条件查询
        if (request.getUserId() != null) {
            queryWrapper.eq("user_id", request.getUserId());
        }
        if (request.getCategoryId() != null) {
            queryWrapper.eq("category_id", request.getCategoryId());
        }
        if (request.getIsPinned() != null) {
            queryWrapper.eq("is_pinned", request.getIsPinned());
        }
        if (request.getIsPublished() != null) {
            queryWrapper.eq("is_published", request.getIsPublished());
        }
        // 排序
        queryWrapper.orderByDesc("is_pinned", "created_at");
        // 分页
        Page<Article> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }


}





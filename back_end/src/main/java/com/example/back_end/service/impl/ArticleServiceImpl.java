package com.example.back_end.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.back_end.mapper.ArticleMapper;
import com.example.back_end.model.domain.Article;
import com.example.back_end.model.domain.request.ArticleQueryRequest;
import com.example.back_end.model.domain.request.ArticleResponse;
import com.example.back_end.service.ArticleService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
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
     * 分页查询文章（带分类名称和作者名称）
     */
    @Override
    public Page<ArticleResponse> getArticlePage(ArticleQueryRequest request) {
        Page<ArticleResponse> page = new Page<>(request.getPageNum(), request.getPageSize());
        
        // 处理关键词
        String keyword = StringUtils.isNotBlank(request.getKeyword()) ? request.getKeyword() : null;
        
        return articleMapper.selectArticlePage(
                page,
                request.getUserId(),
                request.getCategoryId(),
                request.getIsPinned(),
                request.getIsPublished(),
                keyword
        );
    }
}

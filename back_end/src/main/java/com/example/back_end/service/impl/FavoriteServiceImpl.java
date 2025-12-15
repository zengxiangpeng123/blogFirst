package com.example.back_end.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.back_end.mapper.FavoriteMapper;
import com.example.back_end.model.domain.Favorite;
import com.example.back_end.model.domain.request.FavoriteQueryRequest;
import com.example.back_end.service.FavoriteService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
* @author 15173
* @description 针对表【favorite(收藏表)】的数据库操作Service实现
* @createDate 2025-12-12 15:15:22
*/
@Service
public class FavoriteServiceImpl extends ServiceImpl<FavoriteMapper, Favorite>
    implements FavoriteService {
    @Resource
    private FavoriteMapper favoriteMapper;

    @Override
    public Page<Favorite> getFavoriteArticles(FavoriteQueryRequest request) {
        QueryWrapper<Favorite> queryWrapper = new QueryWrapper<>();

        // 条件查询
        if (request.getUserId() != null) {
            queryWrapper.eq("user_id", request.getUserId());
        }
        if (request.getCategoryId() != null) {
            queryWrapper.eq("category_id", request.getCategoryId());
        }

        // 排序
        queryWrapper.orderByDesc("is_pinned", "created_at");
        // 分页
        Page<Favorite> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

}





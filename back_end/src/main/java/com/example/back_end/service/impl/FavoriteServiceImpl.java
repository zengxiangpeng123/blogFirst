package com.example.back_end.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.back_end.common.ErrorCode;
import com.example.back_end.exceptionHandler.UserOperationException;
import com.example.back_end.mapper.FavoriteMapper;
import com.example.back_end.model.domain.Favorite;
import com.example.back_end.model.domain.request.FavoriteQueryRequest;
import com.example.back_end.model.domain.response.FavoriteResponse;
import com.example.back_end.service.FavoriteService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

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
/*        QueryWrapper<Favorite> queryWrapper = new QueryWrapper<>();

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
        return this.page(page, queryWrapper);*/
        return null;
    }


    /**
     * 收藏文章 或者取消收藏（根据数据库实际状态切换）
     * @param request 收藏参数
     * @return 收藏状态：1-已收藏，0-未收藏
     */
    @Override
    public Integer clickFavorite(FavoriteQueryRequest request) {
        Long userId = request.getUserId();
        Long articleId = request.getArticleId();
        // 校验参数
        if (userId == null || articleId == null) {
            throw new UserOperationException(ErrorCode.PARAM_ERROR, "收藏参数不能为空!");
        }

        //数据库查询当前收藏记录
        QueryWrapper<Favorite> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("article_id", articleId);
        List<Favorite> list = this.list(queryWrapper);
        
        if (list == null || list.isEmpty()) {
            // 没有记录，新增收藏
            Favorite favorite = new Favorite();
            favorite.setUserId(userId);
            favorite.setArticleId(articleId);
            favorite.setIsExist(1);  // 收藏
            this.save(favorite);
            return 1;
        } else {
            // 有记录，根据当前状态切换
            Favorite favorite = list.getFirst();
            int newStatus = favorite.getIsExist() == 1 ? 0 : 1;  // 取反
            favorite.setIsExist(newStatus);
            this.updateById(favorite);
            return newStatus;
        }
    }

    /**
     * 获取用户收藏列表
     * @param userId 用户ID
     * @return 收藏的文章列表
     */
    @Override
    public List<FavoriteResponse> getFavoriteList(Long userId) {
        return favoriteMapper.selectFavoriteList(userId);
    }
}





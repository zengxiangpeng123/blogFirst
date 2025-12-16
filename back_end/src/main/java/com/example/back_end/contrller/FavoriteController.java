package com.example.back_end.contrller;

import com.example.back_end.common.ErrorCode;
import com.example.back_end.common.Result;
import com.example.back_end.exceptionHandler.UserOperationException;
import com.example.back_end.model.domain.request.FavoriteQueryRequest;
import com.example.back_end.model.domain.response.FavoriteResponse;
import com.example.back_end.service.FavoriteService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorite")
public class FavoriteController {
    @Resource
    private FavoriteService favoriteService;

    /**
     * 实现收藏文章与取消收藏文章的功能
     * 其中 0是取消收藏，1是收藏
     * @param request 收藏请求参数
     * @return 收藏状态：1-已收藏，0-未收藏
     */
    @PostMapping("/click")
    public Result<Integer> clickFavorite(@RequestBody FavoriteQueryRequest request) {
        if(request == null || request.getUserId() == null || request.getArticleId() == null){
            throw new UserOperationException(ErrorCode.PARAM_ERROR);
        }
        Integer status = favoriteService.clickFavorite(request);
        return Result.success(status);
    }

    /**
     * 获取用户收藏列表
     * @param userId 用户ID
     * @return 收藏的文章列表
     */
    @GetMapping("/list")
    public Result<List<FavoriteResponse>> getFavoriteList(@RequestParam Long userId) {
        if (userId == null) {
            throw new UserOperationException(ErrorCode.PARAM_ERROR, "用户ID不能为空");
        }
        List<FavoriteResponse> list = favoriteService.getFavoriteList(userId);
        return Result.success(list);
    }
}

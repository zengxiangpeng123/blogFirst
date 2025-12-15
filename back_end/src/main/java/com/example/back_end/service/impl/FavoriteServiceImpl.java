package com.example.back_end.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.back_end.mapper.FavoriteMapper;
import com.example.back_end.model.domain.Favorite;
import com.example.back_end.service.FavoriteService;
import org.springframework.stereotype.Service;

/**
* @author 15173
* @description 针对表【favorite(收藏表)】的数据库操作Service实现
* @createDate 2025-12-12 15:15:22
*/
@Service
public class FavoriteServiceImpl extends ServiceImpl<FavoriteMapper, Favorite>
    implements FavoriteService {

}





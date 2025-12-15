package com.example.back_end.contrller;

import com.example.back_end.model.domain.request.FavoriteQueryRequest;
import com.example.back_end.service.FavoriteService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/favorite")
public class FavoriteController {
    @Resource
    private FavoriteService favoriteService;

    @PostMapping("/add")
    public void addArticle(FavoriteQueryRequest request) {

    }
}

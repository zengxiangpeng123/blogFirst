package com.example.back_end.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.back_end.mapper.ArticleMapper;
import com.example.back_end.model.dao.Article;
import com.example.back_end.service.ArticleService;
import org.springframework.stereotype.Service;

/**
* @author 15173
* @description 针对表【article(文章表)】的数据库操作Service实现
* @createDate 2025-12-12 15:15:21
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService {

}





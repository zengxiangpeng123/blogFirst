package com.example.back_end.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.back_end.mapper.CategoryMapper;
import com.example.back_end.model.dao.Category;
import com.example.back_end.service.CategoryService;
import org.springframework.stereotype.Service;

/**
* @author 15173
* @description 针对表【category(分类表)】的数据库操作Service实现
* @createDate 2025-12-12 15:15:22
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService {

}





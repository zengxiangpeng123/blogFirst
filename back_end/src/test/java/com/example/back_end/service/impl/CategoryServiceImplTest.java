package com.example.back_end.service.impl;


import com.example.back_end.mapper.CategoryMapper;
import com.example.back_end.model.dao.Category;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CategoryServiceImplTest {
    @Resource
    private CategoryMapper categoryMapper;
    @Test
    public void getByIdTest() {
        List<Category> categories = categoryMapper.selectList(null);
        System.out.println(categories);

    }

}
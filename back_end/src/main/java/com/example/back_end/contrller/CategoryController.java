package com.example.back_end.contrller;

import com.example.back_end.service.CategoryService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    //需要写一个方法,用来获取所有分类信息

    //写一个方法,用来判断分类是否存在,不存在则创建,


}

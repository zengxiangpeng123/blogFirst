package com.example.back_end.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.back_end.mapper.UserMapper;
import com.example.back_end.model.dao.User;
import com.example.back_end.service.UserService;
import org.springframework.stereotype.Service;

/**
* @author 15173
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2025-12-12 15:15:22
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

}





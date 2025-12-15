package com.example.back_end.service.impl;


import cn.hutool.core.util.ReUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.back_end.mapper.UserMapper;
import com.example.back_end.model.domain.User;
import com.example.back_end.service.UserService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author 15173
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2025-12-12 15:15:22
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return -1;
        }

        if (userAccount.length() < 4) {
            return -1;
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            return -1;
        }

        //用户不重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userAccount);
        long count = this.count(queryWrapper);
        if (count > 0) {
            return -1;
        }

        //账号不能包含特殊字符
        String regex = "^[a-zA-Z0-9_]{3,20}$";
        boolean match = ReUtil.isMatch(regex, userAccount);
        if (!match) {
            return -1;
        }

        // 密码与效验密码一致
        if (!userPassword.equals(checkPassword)) {
            return -1;
        }

        //

        return 0;
    }
}





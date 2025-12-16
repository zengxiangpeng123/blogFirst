package com.example.back_end.service.impl;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ReUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.back_end.common.ErrorCode;
import com.example.back_end.exceptionHandler.LoginException;
import com.example.back_end.exceptionHandler.UserOperationException;
import com.example.back_end.mapper.UserMapper;
import com.example.back_end.model.domain.User;
import com.example.back_end.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

import static com.example.back_end.contant.UserConstant.USER_LOGIN_STATE;

/**
 * @author zeng
 * @description 针对表【user(用户表)】的数据库操作Service实现
 * @createDate 2025-12-12 15:15:22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    /**
     * 盐值,用来加密密码
     */
    final static String sALT1 = "yu";
    final static String sALT2 = "le";
    @Resource
    private UserMapper userMapper;

    /**
     *  获取当前登录用户信息
     * @param request 请求
     * @return 当前登录用户信息(脱敏)
     */
    @Override
    public User getCurrentUser(HttpServletRequest request) {
        if(request == null){
            throw new UserOperationException(ErrorCode.NULL_ERROR,"请求为空!请先登录");
        }
        User user = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if(user == null){
            throw new UserOperationException(ErrorCode.NOT_LOGIN,"未登录!请先登录");
        }
        return this.getSafetyUser(user);
    }
    /**
     * 用户的注册
     *
     * @param username      用户名
     * @param userPassword  密码
     * @param checkPassword 确认密码
     * @return 注册结果
     */
    @Override
    public Long userRegister(String username, String userPassword, String checkPassword) {
        //1.参数校验
        if (StringUtils.isAnyBlank(username, userPassword, checkPassword)) {
            throw new LoginException(ErrorCode.PARAM_ERROR, "用户名或密码不能为空");
        }

        if (username.length() < 4) {
            throw new LoginException(ErrorCode.PARAM_ERROR, "用户名长度不能小于4");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new LoginException(ErrorCode.PARAM_ERROR, "密码长度不能小于8");
        }

        //账号不能包含特殊字符
        String regex = "^[a-zA-Z0-9_]{3,20}$";
        boolean match = ReUtil.isMatch(regex, username);
        if (!match) {
            throw new LoginException(ErrorCode.PARAM_ERROR, "用户名只能包含字母、数字、下划线，长度3-20位");
        }

        // 密码与效验密码一致
        if (!userPassword.equals(checkPassword)) {
            throw new LoginException(ErrorCode.PARAM_ERROR, "两次密码输入不一致");
        }

        //用户不重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        long count = this.count(queryWrapper);
        if (count > 0) {
            throw new LoginException(ErrorCode.PARAM_ERROR, "用户名已存在");
        }
        //2.加密密码
        String encryptedPassword = DigestUtils.md5DigestAsHex((sALT1 + userPassword + sALT2).getBytes(StandardCharsets.UTF_8));

        //3.插入数据库
        User user = new User();
        user.setUsername(username);
        user.setPassword(encryptedPassword);

        //4.设置默认名字
        //todo 这个后面加一个表单单独设置  or  或者在个人设置中改写
        user.setNickname(username);
        user.setEmail("");
        user.setAvatar("https://pic.pngsucai.com/00/92/69/8a978d21135564ff.webp");
        user.setBio("");
        user.setCreatedAt(DateUtil.date());
        user.setUpdatedAt(DateUtil.date());
        boolean save = this.save(user);
        if (!save) {
            throw new LoginException(500, "用户注册失败,请联系管理员!", "用户注册失败,请联系管理员");
        }

        return count;
    }

    /**
     * 用户的登录
     * @param username    用户名
     * @param userPassword 密码
     * @param request    请求
     * @return  登录结果
     */
    @Override
    public User userLogin(String username, String userPassword, HttpServletRequest request) {
        //1.参数校验
        if (StringUtils.isAnyBlank(username, userPassword)) {
            throw new LoginException(ErrorCode.PARAM_ERROR, "用户名或密码不能为空");
        }

        if (username.length() < 4) {
            throw new LoginException(ErrorCode.PARAM_ERROR, "用户名长度不能小于4");
        }

        if (userPassword.length() < 8) {
            throw new LoginException(ErrorCode.PARAM_ERROR, "密码长度不能小于8");
        }

        //账号不能包含特殊字符
        String regex = "^[a-zA-Z0-9_]{3,20}$";
        boolean match = ReUtil.isMatch(regex, username);
        if (!match) {
            throw new LoginException(ErrorCode.PARAM_ERROR, "用户名只能包含字母、数字、下划线，长度3-20位");
        }
        //2.加密密码
        String encryptedPassword = DigestUtils.md5DigestAsHex((sALT1 + userPassword + sALT2).getBytes(StandardCharsets.UTF_8));

        //用户不重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        queryWrapper.eq("password", encryptedPassword);
        User dbUser = this.getOne(queryWrapper);
        if (dbUser == null) {
            throw new LoginException(403, "用户名或密码错误");
        }

        //3.用户信息脱敏
        User safeUser = getSafetyUser(dbUser);

        //4.设置session
        request.getSession().setAttribute(USER_LOGIN_STATE, safeUser);

        //5.返回结果
        return safeUser;


    }

    @Override
    public void userLogout(HttpServletRequest request) {
        //清除session,退出登录
        request.getSession().setAttribute(USER_LOGIN_STATE,null);
    }


    /**
     * 获取脱敏后的用户信息
     *
     * @param user 用户信息
     * @return 脱敏后的用户信息
     */
    @Override
    public User getSafetyUser(User user) {
        User safeUser = new User();
        safeUser.setId(user.getId());             //主键
        safeUser.setUsername(user.getUsername()); //用户名
        safeUser.setNickname(user.getNickname()); //昵称
        safeUser.setEmail(user.getEmail());       //邮箱
        safeUser.setAvatar(user.getAvatar());     //头像
        safeUser.setBio(user.getBio());           //简介
        safeUser.setCreatedAt(user.getCreatedAt());//创建时间
        safeUser.setUpdatedAt(user.getUpdatedAt());//更新时间

        return safeUser;
    }
}





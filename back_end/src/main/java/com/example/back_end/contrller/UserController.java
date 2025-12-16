package com.example.back_end.contrller;

import com.example.back_end.common.ErrorCode;
import com.example.back_end.common.Result;
import com.example.back_end.exceptionHandler.LoginException;
import com.example.back_end.exceptionHandler.UserOperationException;
import com.example.back_end.model.domain.User;
import com.example.back_end.model.domain.request.UserLoginRequest;
import com.example.back_end.model.domain.request.UserRegisterRequest;
import com.example.back_end.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import static com.example.back_end.contant.UserConstant.USER_LOGIN_STATE;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册
     * @param userRegisterRequest 用户注册请求
     * @return 注册成功的用户编号
     */
    @PostMapping("/register")
    public Result<Long> register(@RequestBody UserRegisterRequest userRegisterRequest) {
        if(userRegisterRequest == null){
            throw new LoginException(ErrorCode.NULL_ERROR,"用户信息为空");
        }
        if(StringUtils.isAnyBlank(userRegisterRequest.getUsername(),userRegisterRequest.getPassword(),userRegisterRequest.getCheckPassword())){
            throw new LoginException(ErrorCode.PARAM_ERROR,"用户名或密码为空");
        }
        String username = userRegisterRequest.getUsername();
        String password = userRegisterRequest.getPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();

        Long number = userService.userRegister(username, password, checkPassword);

        return Result.success(number);
    }

    /**
     * 用户登录
     * @param userLoginRequest 用户登录请求
     * @param request http请求
     * @return 登录成功的用户信息
     * @throws LoginException 登录异常
     */
    @PostMapping("/login")
    public Result<User> login(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new LoginException(ErrorCode.NULL_ERROR, "用户信息为空");
        }
        String username = userLoginRequest.getUsername();
        String password = userLoginRequest.getPassword();
        if(StringUtils.isAnyBlank(username,password)){
            throw new LoginException(ErrorCode.PARAM_ERROR,"用户名或密码为空");
        }

        return Result.success(userService.userLogin(username, password, request));
    }

    @PostMapping("/logout")
    public Result<Boolean> logout(HttpServletRequest request) {
        if(request == null){
            throw new LoginException(ErrorCode.NULL_ERROR,"请求为空");
        }
        request.getSession().setAttribute(USER_LOGIN_STATE, null);

        return Result.success(true);
    }

    /**
     * 获取当前用户信息
     * @param request http请求
     * @return 当前用户信息(安全用户信息)
     * @throws UserOperationException 登录异常
     */
    @PostMapping("/current")
    public Result<User> getCurrentUser(HttpServletRequest request) {
        if(request == null){
            throw new UserOperationException(ErrorCode.NULL_ERROR,"请求为空!");
        }
        User safetyUser = new User();
        try{
             safetyUser = userService.getCurrentUser(request);

        }catch (UserOperationException e){
            throw new UserOperationException(ErrorCode.NOT_LOGIN,"用户没有登录,请登录!!");
        }
        return Result.success(safetyUser);
    }

    @PutMapping("/update")
    public Result<User> updateUser(@RequestBody User user,HttpServletRequest request) {
        if(user == null){
            throw new UserOperationException(ErrorCode.PARAM_ERROR,"用户信息为空!");
        }
        User currentUser = userService.getCurrentUser(request);
        if(currentUser == null){
            throw new UserOperationException(ErrorCode.NOT_LOGIN,"用户没有登录,请登录!!");
        }
        user.setId(currentUser.getId());
        boolean b = userService.updateById(user);
        if(b){
            User safetyUser = userService.getSafetyUser(user);
            return Result.success(safetyUser);
        }else{
            throw new UserOperationException(ErrorCode.NULL_ERROR,"用户操作错误!!!");
        }
    }
}

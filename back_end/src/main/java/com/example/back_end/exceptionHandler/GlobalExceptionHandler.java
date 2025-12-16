package com.example.back_end.exceptionHandler;

/*
 * 全局异常处理类
 * */

import com.example.back_end.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.example.back_end.contrller")
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 用户操作异常处理（包括未登录）
     *
     * @param e 用户操作异常
     * @return 返回错误信息
     */
    @ExceptionHandler(UserOperationException.class)
    @ResponseBody
    public Result<String> userOperationError(UserOperationException e) {
        log.error("用户操作异常: " + e.getMessage(), e);
        return Result.error(e.getCode(), e.getMessage());
    }

    /**
     * 全局异常处理方法
     *
     * @param e 异常
     * @return 返回错误信息
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<String> error(Exception e) {
        log.error("系统异常: " + e.getMessage(), e);
        return Result.error("系统异常");
    }

    /**
     * 客户端异常处理方法
     *
     * @param e 客户端异常
     * @return 返回错误信息
     */
    @ExceptionHandler(CustomerException.class)
    @ResponseBody
    public Result<String> customerError(CustomerException e) {
        log.error("自定义异常: " + e.getMessage(), e);
        return Result.error(e.getCode(), e.getMessage());
    }

    /**
     * 登录时异常
     * @param e 登录异常
     * @return 返回错误信息
     */
    @ExceptionHandler(LoginException.class)
    @ResponseBody
    public Result<String> loginError(LoginException e) {
        log.error("登录异常: " + e.getMessage(), e);
        return Result.error(e.getCode(), e.getMessage());
    }


}

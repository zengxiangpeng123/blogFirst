package com.example.back_end.common;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 通用返回结果类
 * @param <T>
 */
@Data
public class Result<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = -1083001840855050958L;

    private int code;
    
    private T data;
    
    private String message;

    private String description;


    // 成功返回结果
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("请求成功");
        return result;
    }
    public static <T> Result<T> success(ErrorCode errorCode) {
        Result<T> result = success();
        result.setCode(errorCode.getCode());
        result.setMessage(errorCode.getMessage());
        return result;
    }

    public static <T> Result<T> success(ErrorCode errorCode,T data) {
        Result<T> result = success();
        result.setCode(errorCode.getCode());
        result.setMessage(errorCode.getMessage());
        result.setData(data);
        return result;
    }

    public static <T> Result<T> success(ErrorCode errorCode,T data,String message) {
        Result<T> result = success();
        result.setCode(errorCode.getCode());
        result.setMessage(errorCode.getMessage());
        result.setData(data);
        result.setMessage(message);
        return result;
    }
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setData(data);
        result.setMessage("请求成功");
        return result;
    }

    public static <T> Result<T> success(T data,String message){
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setData(data);
        result.setMessage(message);
        return result;
    }

    // 失败返回结果
    public static <T> Result<T> error(String message) {
        Result<T> result = new Result<>();
        result.setCode(500);
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> error(int code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> error(ErrorCode errorCode) {
        Result<T> result = new Result<>();
        result.setCode(errorCode.getCode());
        result.setMessage(errorCode.getMessage());
        return result;
    }
}

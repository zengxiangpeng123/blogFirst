package com.example.back_end.common;

/**
 * @author zeng
 */
public enum ErrorCode {
    SUCCESS(200, "成功", "成功"),
    PARAM_ERROR(40000, "参数错误", "参数错误"),
    NULL_ERROR(40001, "空指针错误", "空指针错误"),
    NOT_LOGIN(40100, "未登录", "未登录"),
    NO_AUTH(40101, "没有权限", "没有权限"),
    ;


    private final int code;
    private final String message;
    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}

package com.example.back_end.exceptionHandler;


import com.example.back_end.common.ErrorCode;
import lombok.Data;

import java.io.Serial;

/**
 * 登录异常
 * 运行时异常
 */
@Data
public class UserOperationException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -5819454563442351432L;
    private String message;
    private int code;
    private String description;

    public UserOperationException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public UserOperationException(String message) {
        this.code = 500;
        this.message = message;
    }

    public UserOperationException(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public UserOperationException() {

    }

    public UserOperationException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.description = errorCode.getDescription();
    }

    public UserOperationException(ErrorCode errorCode, String message) {
        this.code = errorCode.getCode();
        this.message = message!=null?message:errorCode.getMessage();
        this.description = errorCode.getDescription();
    }
    public UserOperationException(ErrorCode errorCode, String message, String description) {
        this.code = errorCode.getCode();
        this.message = message!= null ? message : errorCode.getMessage();
        this.description = description != null ? description : errorCode.getDescription();
    }

}

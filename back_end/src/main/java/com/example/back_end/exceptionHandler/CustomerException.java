package com.example.back_end.exceptionHandler;


import com.example.back_end.common.ErrorCode;
import lombok.Data;

import java.io.Serial;

/**
 * 自定义异常
 * 运行时异常
 */
@Data
public class CustomerException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -5819454563442351432L;
    private String message;
    private int code;
    private String description;

    public CustomerException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public CustomerException(String message) {
        this.code = 500;
        this.message = message;
    }

    public CustomerException(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public CustomerException() {

    }

    public CustomerException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.description = errorCode.getDescription();
    }
}

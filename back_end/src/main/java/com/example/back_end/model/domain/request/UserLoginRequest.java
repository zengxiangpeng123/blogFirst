package com.example.back_end.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 *登录的请求对象
 */
@Data
public class UserLoginRequest implements Serializable {


    @Serial
    private static final long serialVersionUID = 4261443420154401808L;
    private String username;

    private String password;

}

package com.example.back_end.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 注册请求对象
 */
@Data
public class UserRegisterRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 7401555259257861093L;

    private String username;

    private String password;

    private String checkPassword;

//    private String plantCode;
}

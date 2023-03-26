package com.example.wz001.common;

import lombok.Data;

import java.io.Serializable;

//通用返回类
@Data
public class BaseResponse<T> implements Serializable {

    private int code;
    private String msg;
    private T data;

    public BaseResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public BaseResponse(int code, T data) {
        this.code = code;
        this.data = data;
        this.msg ="";
    }
    public BaseResponse(ErrorCode errorCode) {

        this(errorCode.getCode(), errorCode.getMsg(),null);
    }
}

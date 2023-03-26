package com.example.wz001.common;

public class ResultUtils {

    //成功返回
    public static <T> BaseResponse<T> success(T data) {

        return new BaseResponse<>(0, "ok",data);
    }
}

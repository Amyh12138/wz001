package com.example.wz001.exception;

import com.example.wz001.common.ErrorCode;

public class ParamsExceptions extends RuntimeException{
    private Integer code = 300;
    private String msg ="参数异常";

    public ParamsExceptions() {
        super("参数异常");
    }
    public ParamsExceptions(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
    }
    public ParamsExceptions( String msg) {
        super(msg);
        this.msg = msg;
    }
    public ParamsExceptions(ErrorCode errorCode, String msg) {
        super(msg);
        this.msg = msg;
        this.code = errorCode.getCode();
    }

    public Integer getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }


}

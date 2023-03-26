package com.example.wz001.util;

import com.example.wz001.exception.ParamsExceptions;

public class AssertUtil {
    public static void isTrue(boolean flag,String msg){
        if(flag){
            throw new ParamsExceptions(msg);

        }
    }

}

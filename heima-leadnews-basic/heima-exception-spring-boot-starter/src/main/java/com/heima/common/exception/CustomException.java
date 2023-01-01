package com.heima.common.exception;

import com.heima.model.common.enums.AppHttpCodeEnum;

/**
 * @Author: njy
 * @Date: 2023/1/2 - 01 - 02 - 0:45
 * @Description: com.heima.common.exception
 * @version: 1.0
 */
public class CustomException extends RuntimeException{
    // 异常处理的枚举
    private AppHttpCodeEnum appHttpCodeEnum;

    public CustomException(AppHttpCodeEnum appHttpCodeEnum) {
        this.appHttpCodeEnum = appHttpCodeEnum;
    }
    public CustomException(AppHttpCodeEnum appHttpCodeEnum,String msg) {
        appHttpCodeEnum.setErrorMessage(msg);
        this.appHttpCodeEnum = appHttpCodeEnum;
    }
    public AppHttpCodeEnum getAppHttpCodeEnum() {
        return appHttpCodeEnum;
    }
}

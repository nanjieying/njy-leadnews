package com.heima.common.exception;

import com.heima.model.common.enums.AppHttpCodeEnum;

/**
 * @Author: njy
 * @Date: 2023/1/2 - 01 - 02 - 0:50
 * @Description: com.heima.common.exception
 * @version: 1.0
 */
public class CustException {
    public static void cust(AppHttpCodeEnum codeEnum) {
        throw new CustomException(codeEnum );
    }
    public static void cust(AppHttpCodeEnum codeEnum,String msg) {
        throw new CustomException(codeEnum,msg);
    }
}

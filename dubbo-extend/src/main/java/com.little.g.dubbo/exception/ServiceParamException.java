package com.little.g.dubbo.exception;


import com.little.g.common.ResultJson;

/**
 * SOA层发生参数验证不合法时抛出此异常.
 * Created by mayan on 15/4/03.
 */
public class ServiceParamException extends RuntimeException {

    public ServiceParamException(String message) {
        super(message);
    }
    
    public ServiceParamException(String message, Throwable cause) {
		super(message, cause);
	}

    public ServiceParamException(Integer code) {
        super(ResultJson.msg.get(code));
    }
}

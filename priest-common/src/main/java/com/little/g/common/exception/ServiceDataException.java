package com.little.g.common.exception;

/**
 * SOA层发生业务数据不合法时抛出此异常.
 * Created by keysilence on 14-10-20.
 *
 * optimize 'default message is code'
 * modify by chris.wang 2016-05-17
 */
public class ServiceDataException extends RuntimeException {

    private static final long serialVersionUID = 3895981970668254319L;

    private Integer code;

    public ServiceDataException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceDataException(String message, Throwable cause) {
		super(message, cause);
	}

    public ServiceDataException(Integer code) {
        super(String.valueOf(code));
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

package com.little.g.demo.web;

import com.little.g.common.ResultJson;
import com.little.g.common.exception.ServiceDataException;
import org.apache.dubbo.rpc.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Resource
    private MessageSource messageSource;

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    /**
     * 用于处理通用异常
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResultJson bindException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();

        String errorMesssage = "";

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMesssage += fieldError.getDefaultMessage() + ", ";
        }
        ResultJson r=new ResultJson();
        r.setC(ResultJson.INVALID_PARAM);
        r.setM(errorMesssage);
        return r;
}

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultJson jsonErrorHandler(HttpServletRequest req, Exception e){
        ResultJson r=new ResultJson();
        r.setC(ResultJson.SYSTEM_UNKNOWN_EXCEPTION);
        if(e instanceof RpcException){
            RpcException rpc= (RpcException) e;
            r.setC(rpc.getCode());
            r.setM(rpc.getMessage());
            log.error("Request RpcException url:{},e",req.getRequestURI(),e.getMessage());
        }else if(e instanceof ServiceDataException) {
            ServiceDataException service= (ServiceDataException) e;
            r.setC(service.getCode());
            r.setM(service.getMessage());
            log.error("Request ServiceDataException url:{},e",req.getRequestURI(),e.getMessage());
        }else {
            log.error("Request exception url:{},e",req.getRequestURI(),e);
        }
        return r;
    }

}
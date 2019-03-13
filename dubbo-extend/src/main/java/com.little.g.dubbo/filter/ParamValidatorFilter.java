package com.little.g.dubbo.filter;

import com.little.g.common.ResultJson;
import com.little.g.dubbo.utils.JSR303Util;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.Constants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * 增加方法参数输出,方便定位问题
 * modify by wangzhen on 2016-02-04
 * Created by mayan on 15-3-28.
 */
@Activate(group = Constants.PROVIDER)
public class ParamValidatorFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(ParamValidatorFilter.class);


    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        try {
            // metadata
            Class<?> clazz = invoker.getInterface();
            String methodName = invocation.getMethodName();
            Class<?>[] pts = invocation.getParameterTypes();
            Method invokeMethod = clazz.getDeclaredMethod(methodName, pts);

            // runtime
            Annotation[][] annoss = invokeMethod.getParameterAnnotations();
            Object[] args = invocation.getArguments();
//            String[] parameterNames = parameterNameDiscoverer.getParameterNames(invokeMethod);

            int idx = 0;
            if (annoss != null && annoss.length > 0) {
                for (Annotation[] annos : annoss) {
                    Object arg = args[idx];
                    if (annos != null && annos.length > 0) {
                        for (Annotation annotation : annos) {
                            String simpleName = annotation.annotationType().getName();
                            String error=null;
                                if (simpleName.indexOf("javax.validation")>=0) {
                                    if("javax.validation.Valid".equals(simpleName)){
                                        error = JSR303Util.validateParams(arg);
                                    }else {
                                        try {
                                            error = JSR303Util.validateParams(arg, annotation.getClass());
                                        }catch (IllegalArgumentException e){
                                            error=e.getMessage();
                                        }

                                    }
                                    boolean flag= StringUtils.isEmpty(error);

                                    logger.debug(String.format("访问注解[%s] 方法[%s] 验证结果[%s]", annotation, methodName, error));

                                    if (!flag) {

                                        String result;
                                        if("javax.validation.Valid".equals(simpleName)){
                                            result=error;
                                        }else {
                                            Method method = annotation.getClass().getDeclaredMethod("message");

                                            logger.debug("调用annotation:{}方法:{}", annotation, method);

                                            result = (String) method.invoke(annotation);
                                        }




                                        logger.error(String.format("方法[%s]第[%d]个参数遇到问题", methodName, idx));

                                        throw new RpcException(ResultJson.INVALID_PARAM, String.format("方法[%s]第[%d]个参数遇到问题", methodName, idx));
                                    }
                                } else {
                                    logger.warn("plz config validator in validator-config.xml!");
                                }
                            }
                        }
                    }
                    idx++;
                }

        } catch (Exception e) {
            if(e instanceof  RpcException){
                throw new RpcException(e);
            }
            logger.error(e.getMessage(), e);
        }

        return invoker.invoke(invocation);
    }
}

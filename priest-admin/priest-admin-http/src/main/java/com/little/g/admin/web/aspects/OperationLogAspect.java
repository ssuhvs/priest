package com.little.g.admin.web.aspects;


import com.little.g.admin.common.annotation.SelectOne;
import com.little.g.admin.common.utils.JsonUtil;
import com.little.g.admin.dto.AdminUserDTO;
import com.little.g.admin.web.util.LoggerUtil;
import com.little.g.admin.web.util.SessionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.stream.Stream;

import static com.little.g.admin.web.common.AdminConstants.SESSION_USER_KEY;


@Aspect
@Component
public class OperationLogAspect {

    private static final Logger LOG = LoggerFactory.getLogger(OperationLogAspect.class);

    @Pointcut("@annotation(com.little.g.admin.common.annotation.ModuleOperation)")
    public void actionMethod() {
        // AOP切点方法，无需任何内容
    }

    @Before(value = "actionMethod() ")
    public void beforeInvokeActionMethod(JoinPoint joinPoint) {
//		LoggerUtil.createLoggerAction(joinPoint);
        System.out.printf("point:"+joinPoint.toString());

    }

    @AfterReturning(value = "actionMethod() ", returning = "returnValue")
    public void afterInvokeActionMethod(JoinPoint joinPoint, Object returnValue) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        AdminUserDTO sessionUser = SessionUtils.getSessionValue(request, SESSION_USER_KEY);
        if (sessionUser != null) {
            LoggerUtil.createLoggerAction(joinPoint, sessionUser.getUsername(), sessionUser.getId().intValue());
//            System.out.println("sessionUser:" + sessionUser.toString());
        }
    }

    private Object getObject(Object instance, Object argument) {
        if (instance == null || argument == null) {
            return null;
        }
        Method selectOneMethod = getSelectObjectMethod(instance);
        if (selectOneMethod == null) {
            return null;
        }

        if (argument.getClass().isArray()) {
            Object[] array = (Object[]) argument;
            return Stream.of(array)
                    .map(arg -> {
                        return selectObject(instance, selectOneMethod, getId(arg));
                    })
                    .toArray();
        }
        Long id = getId(argument);
        return selectObject(instance, selectOneMethod, id);
    }

    private Object selectObject(Object instance, Method selectOneMethod, Long id) {
        try {
            return selectOneMethod.invoke(instance, id);
        } catch (Exception e) {
            LOG.info("selectObject exception: -> {} ", e);
        }
        return null;
    }

    private Method getSelectObjectMethod(Object instance) {
        Optional<Method> selectOneMethod = Stream
                .of(instance.getClass().getMethods())
                .filter(method -> {
                    return method.isAnnotationPresent(SelectOne.class);
                })
                .findFirst();
        if (selectOneMethod.isPresent()) {
            return selectOneMethod.get();
        }
        return null;
    }

    private Long getId(Object argument) {
        if (argument instanceof Long) {
            return (Long) argument;
        } else {
            return Long.parseLong(String.valueOf(JsonUtil.object2Map(argument).get("id")));
        }
    }

}

package com.little.g.admin.web.concurrent;


import com.little.g.admin.common.annotation.ModuleManage;
import com.little.g.admin.common.annotation.ModuleOperation;
import com.little.g.admin.common.enums.ModuleType;
import com.little.g.admin.common.enums.OperationType;
import com.little.g.admin.dto.LoggerActionDTO;
import com.little.g.admin.service.LoggerActionService;
import com.little.g.admin.web.util.LoggerUtil;
import com.little.g.admin.web.util.SpringContextUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.aspectj.lang.JoinPoint;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

public class CreateLoggerActionTask implements Callable<LoggerActionDTO> {

//    @Resource
//    LoggerService loggerService;
//
//    @Resource
//    LoggerActionService loggerActionService;


    private JoinPoint joinPoint;
    private String staffName;
    private Integer userId;

    public CreateLoggerActionTask(JoinPoint joinPoint, String staffName, Integer userId) {
        this.joinPoint = joinPoint;
        this.staffName = staffName;
        this.userId = userId;
    }

    @Override
    public LoggerActionDTO call() throws Exception {
        return createLoggerAction();
    }

    private LoggerActionDTO createLoggerAction() {
        ModuleManage moduleManage = LoggerUtil.getModuleManage(this.joinPoint);
        if (moduleManage.ignoreLog()) {
            return null;
        }
        ModuleOperation moduleOperation = LoggerUtil.getModuleOperation(this.joinPoint);
        if (moduleOperation.ignoreLog()) {
            return null;
        }
        OperationType operationType = moduleOperation.value();
//        if (OperationType.GET.equals(operationType)) {
//            return null;
//        }

        List<Object> parList = new ArrayList();
        for (Object param : joinPoint.getArgs()) {
            if (param instanceof Model || param instanceof HttpServletRequest) {
                continue;
            }
            parList.add(param);
        }
        //when
        Date now = new Date();
        //where
        ModuleType moduleType = moduleManage.value();
        String moduleName = moduleType.getMessage();
        //do what
        String description = moduleOperation.description();
        String comment = String.format("【%s】于【%s】在【%s】模块执行了【%s】的操作",
                this.staffName,
                DateFormatUtils.format(now, "yyyy年MM月dd日 HH时mm分ss秒"),
                moduleName,
                description);
        LoggerActionDTO loggerAction = new LoggerActionDTO();
        loggerAction.setStaffName(staffName);
        loggerAction.setUserId(userId);
        loggerAction.setGmtCreate(System.currentTimeMillis());
        loggerAction.setModuleName(moduleName);
        loggerAction.setOperationType(operationType.getCode());
        loggerAction.setOperationDescription(description);
        loggerAction.setComment(comment);
        loggerAction.setContent(parList.toString());
        LoggerActionService loggerService = SpringContextUtil.getBean(LoggerActionService.class);
        loggerService.saveLoggerAction(loggerAction);
        return loggerAction;
    }


    private Object getObject(Object[] array, int index) {
        if (ArrayUtils.isEmpty(array)) {
            return null;
        }
        return array[index];
    }

}

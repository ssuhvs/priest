package com.little.g.admin.web.controllers.security;

import com.little.g.common.dto.Page;
import com.little.g.admin.dto.LoggerActionDTO;
import com.little.g.admin.service.LoggerActionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 日志方法控制器
 *
 * @author liuxl
 * @version loggerActionService.java, v 0.1  2017-6-22 11:04:20
 */
@Controller
@RequestMapping(value = "/admin/loggerAction", method = {RequestMethod.GET, RequestMethod.POST})
public class LoggerActionController {
    private static final Logger logger = LoggerFactory.getLogger(LoggerActionController.class);
    /**
     * 日志方法业务接口
     **/
    @Resource
    private LoggerActionService loggerActionService;

    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(HttpServletRequest request, @RequestParam(value = "currentPage", required = false, defaultValue = "0") int currentPage,
                       Model view) {
        try {
            logger.debug("loggerAction.list start");
            LoggerActionDTO loggerAction = new LoggerActionDTO();
            loggerAction.setPage(currentPage);
            Page<LoggerActionDTO> page = loggerActionService.queryLoggerActionPage(loggerAction);
            logger.debug("loggerAction.list pageResult:{}", page.getResult());

            //放入page对象。
            view.addAttribute("page", page);
            logger.debug("loggerAction.list end");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "/jsp/logger/list";
    }

    @RequestMapping(value = "/edit", method = {RequestMethod.GET})
    public String edit(@RequestParam(value = "id", required = false, defaultValue = "0") Long id,
                       Model view) {
        logger.debug("loggerAction.edit start param.id={}", id);
        try {
            if (id != null && id > 0) {
                LoggerActionDTO loggerAction = loggerActionService.queryLoggerActionById(id);
                view.addAttribute("loggerAction", loggerAction);
            }
            logger.debug("loggerAction.edit end");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "/jsp/logger/edit";
    }
}
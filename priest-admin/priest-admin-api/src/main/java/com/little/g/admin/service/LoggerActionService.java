package com.little.g.admin.service;

import com.little.g.admin.common.page.Page;
import com.little.g.admin.dto.LoggerActionDTO;

import java.util.List;

/**
 * 日志方法业务层访问接口
 * @author  liuxl
 * @version loggerActionService.java, v 0.1  2017-6-21 19:57:08
 */
public interface LoggerActionService
{
	     Long saveLoggerAction(LoggerActionDTO loggerActionDTO);

         Long deleteLoggerAction(Long id);

         LoggerActionDTO queryLoggerActionById(Long id);

         List<LoggerActionDTO> queryLoggerAction(LoggerActionDTO loggerActionDTO);

         Page<LoggerActionDTO> queryLoggerActionPage(LoggerActionDTO loggerActionDTO);
}
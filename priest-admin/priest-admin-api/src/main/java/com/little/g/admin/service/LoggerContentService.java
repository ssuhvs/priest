package com.little.g.admin.service;

import com.little.g.admin.common.page.Page;
import com.little.g.admin.dto.LoggerContentDTO;

import java.util.List;

/**
 * 日志内容业务层访问接口
 * @author  liuxl
 * @version loggerContentService.java, v 0.1  2017-6-21 19:57:08
 */
public interface LoggerContentService
{
	     Long saveLoggerContent(LoggerContentDTO loggerContentDTO);

         Long deleteLoggerContent(Long id);

         LoggerContentDTO queryLoggerContentById(Long id);

         List<LoggerContentDTO> queryLoggerContent(LoggerContentDTO loggerContentDTO);

         Page<LoggerContentDTO> queryLoggerContentPage(LoggerContentDTO loggerContentDTO);
}
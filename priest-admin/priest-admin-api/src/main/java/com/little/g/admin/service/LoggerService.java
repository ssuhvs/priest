package com.little.g.admin.service;

import com.little.g.common.dto.Page;
import com.little.g.admin.dto.LoggerDTO;

import java.util.List;

/**
 * 日志业务层访问接口
 * @author  liuxl
 * @version loggerService.java, v 0.1  2017-6-21 19:57:08
 */
public interface LoggerService
{
	     Long saveLogger(LoggerDTO loggerDTO);

         Long deleteLogger(Long id);

         LoggerDTO queryLoggerById(Long id);

         List<LoggerDTO> queryLogger(LoggerDTO loggerDTO);

         Page<LoggerDTO> queryLoggerPage(LoggerDTO loggerDTO);
}
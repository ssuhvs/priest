package com.little.g.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.little.g.common.dto.Page;
import com.little.g.admin.dto.LoggerDTO;
import com.little.g.admin.mapper.LoggerMapper;
import com.little.g.admin.model.Logger;
import com.little.g.admin.service.LoggerService;
import com.little.g.common.exception.ServiceDataException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * 日志业务层访问接口实现
 * @author  liuxl
 * @version loggerServiceImpl.java, v 0.1  2017-6-21 19:57:08
 */
@Service("loggerService")
public class LoggerServiceImpl implements LoggerService {

private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LoggerServiceImpl.class);
    /**日志数据层访问接口实现**/
	@Autowired
	private LoggerMapper loggerMapper;

    @Override
       public Long saveLogger(LoggerDTO loggerDTO) {
           logger.info("saveLogger loggerDTO:{}", loggerDTO);
                  long rows = 0;
                  try {
                      if (loggerDTO != null) {
                          Logger logger = new Logger();
                          BeanUtils.copyProperties(loggerDTO, logger);
                          if (logger.getId() != null && logger.getId() != 0) {
                              //更新
                              rows = loggerMapper.updateByPrimaryKeySelective(logger);
                          } else {
                              //插入
                              rows = loggerMapper.insert(logger);
                          }
                      }
                  } catch (Exception e) {
                      logger.error(e.getMessage(), e);
                      throw new ServiceDataException("saveLogger Error::", e);
                  }
                  return rows;
       }

       @Override
       public Long deleteLogger(Long id) {
       logger.info("deleteLogger loggerId:{}", id);
           long rows = 0;
           try {
               rows = loggerMapper.deleteByPrimaryKey(id);
           } catch (Exception e) {
               logger.error(e.getMessage(), e);
               throw new ServiceDataException("deletelogger Error::", e);
           }
           return rows;
       }

       @Override
       public LoggerDTO queryLoggerById(Long id) {
       logger.info("queryLoggerById loggerId:{}", id);
           LoggerDTO loggerDTO = null;
           try {
              Logger logger = loggerMapper.selectByPrimaryKey(id);
                           if(logger!=null){
                           loggerDTO = new LoggerDTO();
                               BeanUtils.copyProperties(logger,loggerDTO);
                           }
           } catch (Exception e) {
               logger.error(e.getMessage(), e);
               throw new ServiceDataException("queryloggerById", e);
           }
           return loggerDTO;
       }

       @Override
       public List<LoggerDTO> queryLogger(LoggerDTO loggerDTO) {
            logger.info("queryLogger loggerDTO:{}", loggerDTO);
                  List<LoggerDTO> list = null;
                  List<Logger> loggerList = null;
                  try {
                      if (loggerDTO != null) {
                          Logger logger = new Logger();
                          BeanUtils.copyProperties(loggerDTO, logger);
                          loggerList = loggerMapper.select(logger);
                      } else {
                          Example example = new Example(Logger.class);
                          // 排序规则
                          example.orderBy("id").desc();
                          loggerList = loggerMapper.selectByExample(example);
                      }
                      if (loggerList != null && loggerList.size() > 0) {
                          list = convertLoggerDTO(loggerList);
                      }

                  } catch (Exception e) {
                      logger.error(e.getMessage(), e);
                  }
                  return list;
       }

       @Override
       public Page<LoggerDTO> queryLoggerPage(LoggerDTO loggerDTO) {
        logger.info("queryLoggerPage loggerDTO:{}", loggerDTO);
           Page<LoggerDTO> page = null;
           Integer currentPage = loggerDTO.getPage();
           if (currentPage < 1) currentPage = 1;
           try {
               page = new Page<LoggerDTO>(currentPage, loggerDTO.getPageCount());
               PageHelper.startPage(currentPage, loggerDTO.getPageCount());
               Example example = new Example(Logger.class);
               // 排序规则
               example.orderBy("id").desc();
               List<Logger> list = loggerMapper.selectByExample(example);

               if (list != null && list.size() > 0) {
                   PageInfo pageInfo = new PageInfo(list);
                   page.setTotalCount(Long.valueOf(pageInfo.getTotal()).intValue());
                    List<LoggerDTO> loggerDTOList = convertLoggerDTO(list);
                   page.setResult(loggerDTOList);
               }
           } catch (Exception e) {
               logger.error(e.getMessage(), e);
           }

           return page;
       }
	  private List<LoggerDTO> convertLoggerDTO(List<Logger> loggerList) {
            
            if (loggerList == null) return null;
             List<LoggerDTO> loggerDTOs = new ArrayList<>();
             for (Logger logger : loggerList
                     ) {
                 LoggerDTO loggerDTO = new LoggerDTO();
                 BeanUtils.copyProperties(logger, loggerDTO);
                 loggerDTOs.add(loggerDTO);
             }
             return loggerDTOs;
         }
}
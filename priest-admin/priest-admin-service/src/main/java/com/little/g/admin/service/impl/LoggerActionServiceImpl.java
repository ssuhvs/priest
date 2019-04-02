package com.little.g.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.little.g.admin.common.page.Page;
import com.little.g.admin.dto.LoggerActionDTO;
import com.little.g.admin.mapper.LoggerActionMapper;
import com.little.g.admin.model.LoggerAction;
import com.little.g.admin.service.LoggerActionService;
import com.little.g.common.exception.ServiceDataException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * 日志方法业务层访问接口实现
 * @author  liuxl
 * @version loggerActionServiceImpl.java, v 0.1  2017-6-21 19:57:08
 */
@Service("loggerActionService")
public class LoggerActionServiceImpl implements LoggerActionService {

private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LoggerActionServiceImpl.class);
    /**日志方法数据层访问接口实现**/
	@Autowired
	private LoggerActionMapper loggerActionMapper;

    @Override
       public Long saveLoggerAction(LoggerActionDTO loggerActionDTO) {
           logger.info("saveLoggerAction loggerActionDTO:{}", loggerActionDTO);
                  long rows = 0;
                  try {
                      if (loggerActionDTO != null) {
                          LoggerAction loggerAction = new LoggerAction();
                          BeanUtils.copyProperties(loggerActionDTO, loggerAction);
                          if (loggerAction.getId() != null && loggerAction.getId() != 0) {
                              //更新
                              rows = loggerActionMapper.updateByPrimaryKeySelective(loggerAction);
                          } else {
                              //插入
                              rows = loggerActionMapper.insert(loggerAction);
                          }
                      }
                  } catch (Exception e) {
                      logger.error(e.getMessage(), e);
                      throw new ServiceDataException("saveLoggerAction Error::", e);
                  }
                  return rows;
       }

       @Override
       public Long deleteLoggerAction(Long id) {
       logger.info("deleteLoggerAction loggerActionId:{}", id);
           long rows = 0;
           try {
               rows = loggerActionMapper.deleteByPrimaryKey(id);
           } catch (Exception e) {
               logger.error(e.getMessage(), e);
               throw new ServiceDataException("deleteloggerAction Error::", e);
           }
           return rows;
       }

       @Override
       public LoggerActionDTO queryLoggerActionById(Long id) {
       logger.info("queryLoggerActionById loggerActionId:{}", id);
           LoggerActionDTO loggerActionDTO = null;
           try {
              LoggerAction loggerAction = loggerActionMapper.selectByPrimaryKey(id);
                           if(loggerAction!=null){
                           loggerActionDTO = new LoggerActionDTO();
                               BeanUtils.copyProperties(loggerAction,loggerActionDTO);
                           }
           } catch (Exception e) {
               logger.error(e.getMessage(), e);
               throw new ServiceDataException("queryloggerActionById", e);
           }
           return loggerActionDTO;
       }

       @Override
       public List<LoggerActionDTO> queryLoggerAction(LoggerActionDTO loggerActionDTO) {
            logger.info("queryLoggerAction loggerActionDTO:{}", loggerActionDTO);
                  List<LoggerActionDTO> list = null;
                  List<LoggerAction> loggerActionList = null;
                  try {
                      if (loggerActionDTO != null) {
                          LoggerAction loggerAction = new LoggerAction();
                          BeanUtils.copyProperties(loggerActionDTO, loggerAction);
                          loggerActionList = loggerActionMapper.select(loggerAction);
                      } else {
                          Example example = new Example(LoggerAction.class);
                          // 排序规则
                          example.orderBy("id").desc();
                          loggerActionList = loggerActionMapper.selectByExample(example);
                      }
                      if (loggerActionList != null && loggerActionList.size() > 0) {
                          list = convertLoggerActionDTO(loggerActionList);
                      }

                  } catch (Exception e) {
                      logger.error(e.getMessage(), e);
                  }
                  return list;
       }

       @Override
       public Page<LoggerActionDTO> queryLoggerActionPage(LoggerActionDTO loggerActionDTO) {
        logger.info("queryLoggerActionPage loggerActionDTO:{}", loggerActionDTO);
           Page<LoggerActionDTO> page = null;
           Integer currentPage = loggerActionDTO.getPage();
           if (currentPage < 1) currentPage = 1;
           try {
               page = new Page<LoggerActionDTO>(currentPage, loggerActionDTO.getPageCount());
               PageHelper.startPage(currentPage, loggerActionDTO.getPageCount());
               Example example = new Example(LoggerAction.class);
               // 排序规则
               example.orderBy("id").desc();
               List<LoggerAction> list = loggerActionMapper.selectByExample(example);

               if (list != null && list.size() > 0) {
                   PageInfo pageInfo = new PageInfo(list);
                   page.setTotalCount(Long.valueOf(pageInfo.getTotal()).intValue());
                    List<LoggerActionDTO> loggerActionDTOList = convertLoggerActionDTO(list);
                   page.setResult(loggerActionDTOList);
               }
           } catch (Exception e) {
               logger.error(e.getMessage(), e);
           }

           return page;
       }
	  private List<LoggerActionDTO> convertLoggerActionDTO(List<LoggerAction> loggerActionList) {
            
            if (loggerActionList == null) return null;
             List<LoggerActionDTO> loggerActionDTOs = new ArrayList<>();
             for (LoggerAction loggerAction : loggerActionList
                     ) {
                 LoggerActionDTO loggerActionDTO = new LoggerActionDTO();
                 BeanUtils.copyProperties(loggerAction, loggerActionDTO);
                 loggerActionDTOs.add(loggerActionDTO);
             }
             return loggerActionDTOs;
         }
}
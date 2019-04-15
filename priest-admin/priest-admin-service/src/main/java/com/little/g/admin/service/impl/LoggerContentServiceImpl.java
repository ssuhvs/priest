package com.little.g.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.little.g.common.dto.Page;
import com.little.g.admin.dto.LoggerContentDTO;
import com.little.g.admin.mapper.LoggerContentMapper;
import com.little.g.admin.model.LoggerContent;
import com.little.g.admin.service.LoggerContentService;
import com.little.g.common.exception.ServiceDataException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * 日志内容业务层访问接口实现
 * @author  liuxl
 * @version loggerContentServiceImpl.java, v 0.1  2017-6-21 19:57:08
 */
@Service("loggerContentService")
public class LoggerContentServiceImpl implements LoggerContentService {

private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LoggerContentServiceImpl.class);
    /**日志内容数据层访问接口实现**/
	@Autowired
	private LoggerContentMapper loggerContentMapper;

    @Override
       public Long saveLoggerContent(LoggerContentDTO loggerContentDTO) {
           logger.info("saveLoggerContent loggerContentDTO:{}", loggerContentDTO);
                  long rows = 0;
                  try {
                      if (loggerContentDTO != null) {
                          LoggerContent loggerContent = new LoggerContent();
                          BeanUtils.copyProperties(loggerContentDTO, loggerContent);
                          if (loggerContent.getId() != null && loggerContent.getId() != 0) {
                              //更新
                              rows = loggerContentMapper.updateByPrimaryKeySelective(loggerContent);
                          } else {
                              //插入
                              rows = loggerContentMapper.insert(loggerContent);
                          }
                      }
                  } catch (Exception e) {
                      logger.error(e.getMessage(), e);
                      throw new ServiceDataException("saveLoggerContent Error::", e);
                  }
                  return rows;
       }

       @Override
       public Long deleteLoggerContent(Long id) {
       logger.info("deleteLoggerContent loggerContentId:{}", id);
           long rows = 0;
           try {
               rows = loggerContentMapper.deleteByPrimaryKey(id);
           } catch (Exception e) {
               logger.error(e.getMessage(), e);
               throw new ServiceDataException("deleteloggerContent Error::", e);
           }
           return rows;
       }

       @Override
       public LoggerContentDTO queryLoggerContentById(Long id) {
       logger.info("queryLoggerContentById loggerContentId:{}", id);
           LoggerContentDTO loggerContentDTO = null;
           try {
              LoggerContent loggerContent = loggerContentMapper.selectByPrimaryKey(id);
                           if(loggerContent!=null){
                           loggerContentDTO = new LoggerContentDTO();
                               BeanUtils.copyProperties(loggerContent,loggerContentDTO);
                           }
           } catch (Exception e) {
               logger.error(e.getMessage(), e);
               throw new ServiceDataException("queryloggerContentById", e);
           }
           return loggerContentDTO;
       }

       @Override
       public List<LoggerContentDTO> queryLoggerContent(LoggerContentDTO loggerContentDTO) {
            logger.info("queryLoggerContent loggerContentDTO:{}", loggerContentDTO);
                  List<LoggerContentDTO> list = null;
                  List<LoggerContent> loggerContentList = null;
                  try {
                      if (loggerContentDTO != null) {
                          LoggerContent loggerContent = new LoggerContent();
                          BeanUtils.copyProperties(loggerContentDTO, loggerContent);
                          loggerContentList = loggerContentMapper.select(loggerContent);
                      } else {
                          Example example = new Example(LoggerContent.class);
                          // 排序规则
                          example.orderBy("id").desc();
                          loggerContentList = loggerContentMapper.selectByExample(example);
                      }
                      if (loggerContentList != null && loggerContentList.size() > 0) {
                          list = convertLoggerContentDTO(loggerContentList);
                      }

                  } catch (Exception e) {
                      logger.error(e.getMessage(), e);
                  }
                  return list;
       }

       @Override
       public Page<LoggerContentDTO> queryLoggerContentPage(LoggerContentDTO loggerContentDTO) {
        logger.info("queryLoggerContentPage loggerContentDTO:{}", loggerContentDTO);
           Page<LoggerContentDTO> page = null;
           Integer currentPage = loggerContentDTO.getPage();
           if (currentPage < 1) currentPage = 1;
           try {
               page = new Page<LoggerContentDTO>(currentPage, loggerContentDTO.getPageCount());
               PageHelper.startPage(currentPage, loggerContentDTO.getPageCount());
               Example example = new Example(LoggerContent.class);
               // 排序规则
               example.orderBy("id").desc();
               List<LoggerContent> list = loggerContentMapper.selectByExample(example);

               if (list != null && list.size() > 0) {
                   PageInfo pageInfo = new PageInfo(list);
                   page.setTotalCount(Long.valueOf(pageInfo.getTotal()).intValue());
                    List<LoggerContentDTO> loggerContentDTOList = convertLoggerContentDTO(list);
                   page.setResult(loggerContentDTOList);
               }
           } catch (Exception e) {
               logger.error(e.getMessage(), e);
           }

           return page;
       }
	  private List<LoggerContentDTO> convertLoggerContentDTO(List<LoggerContent> loggerContentList) {
            
            if (loggerContentList == null) return null;
             List<LoggerContentDTO> loggerContentDTOs = new ArrayList<>();
             for (LoggerContent loggerContent : loggerContentList
                     ) {
                 LoggerContentDTO loggerContentDTO = new LoggerContentDTO();
                 BeanUtils.copyProperties(loggerContent, loggerContentDTO);
                 loggerContentDTOs.add(loggerContentDTO);
             }
             return loggerContentDTOs;
         }
}
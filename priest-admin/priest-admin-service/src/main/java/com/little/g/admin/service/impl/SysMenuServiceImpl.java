package com.little.g.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.little.g.common.dto.Page;
import com.little.g.admin.dto.SysMenuDTO;
import com.little.g.admin.mapper.SysMenuMapper;
import com.little.g.admin.model.SysMenu;
import com.little.g.admin.service.SysMenuService;
import com.little.g.common.exception.ServiceDataException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单信息表业务层访问接口实现
 * @author  liuxl
 * @version sysMenuServiceImpl.java, v 0.1  2017-5-8 10:07:46
 */
@Service("sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {

private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SysMenuServiceImpl.class);
    /**菜单信息表数据层访问接口实现**/
	@Autowired
	private SysMenuMapper sysMenuMapper;

    @Override
       public Long saveSysMenu(SysMenuDTO sysMenuDTO) {
           logger.info("saveSysMenu sysMenuDTO:{}", sysMenuDTO);
                  long rows = 0;
                  try {
                      if (sysMenuDTO != null) {
                          SysMenu sysMenu = new SysMenu();
                          BeanUtils.copyProperties(sysMenuDTO, sysMenu);
                          if (sysMenu.getId() != null && sysMenu.getId() != 0) {
                              //更新
                              rows = sysMenuMapper.updateByPrimaryKeySelective(sysMenu);
                          } else {
                              //插入
                              rows = sysMenuMapper.insert(sysMenu);
                          }
                      }
                  } catch (Exception e) {
                      logger.error(e.getMessage(), e);
                      throw new ServiceDataException("saveSysMenu Error::", e);
                  }
                  return rows;
       }

       @Override
       public Long deleteSysMenu(Long id) {
       logger.info("deleteSysMenu sysMenuId:{}", id);
           long rows = 0;
           try {
               rows = sysMenuMapper.deleteByPrimaryKey(id);
           } catch (Exception e) {
               logger.error(e.getMessage(), e);
               throw new ServiceDataException("deletesysMenu Error::", e);
           }
           return rows;
       }

       @Override
       public SysMenuDTO querySysMenuById(Long id) {
       logger.info("querySysMenuById sysMenuId:{}", id);
           SysMenuDTO sysMenuDTO = null;
           try {
              SysMenu sysMenu = sysMenuMapper.selectByPrimaryKey(id);
                           if(sysMenu!=null){
                           sysMenuDTO = new SysMenuDTO();
                               BeanUtils.copyProperties(sysMenu,sysMenuDTO);
                           }
           } catch (Exception e) {
               logger.error(e.getMessage(), e);
               throw new ServiceDataException("querysysMenuById", e);
           }
           return sysMenuDTO;
       }

       @Override
       public List<SysMenuDTO> querySysMenu(SysMenuDTO sysMenuDTO) {
            logger.info("querySysMenu sysMenuDTO:{}", sysMenuDTO);
                  List<SysMenuDTO> list = null;
                  List<SysMenu> sysMenuList = null;
                  try {
                      if (sysMenuDTO != null) {
                          SysMenu sysMenu = new SysMenu();
                          BeanUtils.copyProperties(sysMenuDTO, sysMenu);
                          sysMenuList = sysMenuMapper.select(sysMenu);
                      } else {
                          Example example = new Example(SysMenu.class);
                          // 排序规则
                          example.orderBy("id").desc();
                          sysMenuList = sysMenuMapper.selectByExample(example);
                      }
                      if (sysMenuList != null && sysMenuList.size() > 0) {
                          list = convertSysMenuDTO(sysMenuList);
                      }

                  } catch (Exception e) {
                      logger.error(e.getMessage(), e);
                  }
                  return list;
       }

       @Override
       public Page<SysMenuDTO> querySysMenuPage(SysMenuDTO sysMenuDTO) {
        logger.info("querySysMenuPage sysMenuDTO:{}", sysMenuDTO);
           Page<SysMenuDTO> page = null;
           Integer currentPage = sysMenuDTO.getPage();
           if (currentPage < 1) currentPage = 1;
           try {
               page = new Page<SysMenuDTO>(currentPage, sysMenuDTO.getPageCount());
               PageHelper.startPage(currentPage, sysMenuDTO.getPageCount());
               Example example = new Example(SysMenu.class);
               // 排序规则
               example.orderBy("id").desc();
               List<SysMenu> list = sysMenuMapper.selectByExample(example);

               if (list != null && list.size() > 0) {
                   PageInfo pageInfo = new PageInfo(list);
                   page.setTotalCount(Long.valueOf(pageInfo.getTotal()).intValue());
                    List<SysMenuDTO> sysMenuDTOList = convertSysMenuDTO(list);
                   page.setResult(sysMenuDTOList);
               }
           } catch (Exception e) {
               logger.error(e.getMessage(), e);
           }

           return page;
       }

    @Override
    public List<SysMenuDTO> queryMenuByIds(List<Long> menuIds) {
        Example example = new Example(SysMenu.class);
         example.createCriteria().andIn("id",menuIds);
        List<SysMenu> list = sysMenuMapper.selectByExample(example);
        if(list!=null && list.size() > 0){
            List<SysMenuDTO> menuDTOList = convertSysMenuDTO(list);
            return menuDTOList;
        }
        return null;
    }

    private List<SysMenuDTO> convertSysMenuDTO(List<SysMenu> sysMenuList) {
            
            if (sysMenuList == null) return null;
             List<SysMenuDTO> sysMenuDTOs = new ArrayList<>();
             for (SysMenu sysMenu : sysMenuList
                     ) {
                 SysMenuDTO sysMenuDTO = new SysMenuDTO();
                 BeanUtils.copyProperties(sysMenu, sysMenuDTO);
                 sysMenuDTOs.add(sysMenuDTO);
             }
             return sysMenuDTOs;
         }
}
package com.little.g.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.little.g.admin.common.page.Page;
import com.little.g.admin.dto.SysRoleDTO;
import com.little.g.admin.mapper.SysRoleMapper;
import com.little.g.admin.model.SysRole;
import com.little.g.admin.service.SysRoleService;
import com.little.g.common.exception.ServiceDataException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表业务层访问接口实现
 *
 * @author liuxl
 * @version sysRoleServiceImpl.java, v 0.1  2017-5-8 10:07:46
 */
@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SysRoleServiceImpl.class);
    /**
     * 角色信息表数据层访问接口实现
     **/
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public Long saveSysRole(SysRoleDTO sysRoleDTO) {
        logger.info("saveSysRole sysRoleDTO:{}", sysRoleDTO);
        long rows = 0;
        try {
            if (sysRoleDTO != null) {
                SysRole sysRole = new SysRole();
                BeanUtils.copyProperties(sysRoleDTO, sysRole);
                if (sysRole.getId() != null && sysRole.getId() != 0) {
                    //更新
                    rows = sysRoleMapper.updateByPrimaryKeySelective(sysRole);
                } else {
                    //插入
                    rows = sysRoleMapper.insert(sysRole);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceDataException("saveSysRole Error::", e);
        }
        return rows;
    }

    @Override
    public Long deleteSysRole(Long id) {
        logger.info("deleteSysRole sysRoleId:{}", id);
        long rows = 0;
        try {
            rows = sysRoleMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceDataException("deletesysRole Error::", e);
        }
        return rows;
    }

    @Override
    public SysRoleDTO querySysRoleById(Long id) {
        logger.info("querySysRoleById sysRoleId:{}", id);
        SysRoleDTO sysRoleDTO = null;
        try {
            SysRole sysRole = sysRoleMapper.selectByPrimaryKey(id);
            if (sysRole != null) {
                sysRoleDTO = new SysRoleDTO();
                BeanUtils.copyProperties(sysRole, sysRoleDTO);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceDataException("querysysRoleById", e);
        }
        return sysRoleDTO;
    }

    @Override
    public List<SysRoleDTO> querySysRole(SysRoleDTO sysRoleDTO) {
        logger.info("querySysRole sysRoleDTO:{}", sysRoleDTO);
        List<SysRoleDTO> list = null;
        List<SysRole> sysRoleList = null;
        try {
            if (sysRoleDTO != null) {
                SysRole sysRole = new SysRole();
                BeanUtils.copyProperties(sysRoleDTO, sysRole);
                sysRoleList = sysRoleMapper.select(sysRole);
            } else {
                Example example = new Example(SysRole.class);
                // 排序规则
                example.orderBy("id").desc();
                sysRoleList = sysRoleMapper.selectByExample(example);
            }
            if (sysRoleList != null && sysRoleList.size() > 0) {
                list = convertSysRoleDTO(sysRoleList);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return list;
    }

    @Override
    public Page<SysRoleDTO> querySysRolePage(SysRoleDTO sysRoleDTO) {
        logger.info("querySysRolePage sysRoleDTO:{}", sysRoleDTO);
        Page<SysRoleDTO> page = null;
        Integer currentPage = sysRoleDTO.getPage();
        if (currentPage < 1) currentPage = 1;
        try {
            page = new Page<SysRoleDTO>(currentPage, sysRoleDTO.getPageCount());
            PageHelper.startPage(currentPage, sysRoleDTO.getPageCount());
            Example example = new Example(SysRole.class);
            // 排序规则
            example.orderBy("id").desc();
            List<SysRole> list = sysRoleMapper.selectByExample(example);

            if (list != null && list.size() > 0) {
                PageInfo pageInfo = new PageInfo(list);
                page.setTotalCount(Long.valueOf(pageInfo.getTotal()).intValue());
                List<SysRoleDTO> sysRoleDTOList = convertSysRoleDTO(list);
                page.setResult(sysRoleDTOList);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return page;
    }

    private List<SysRoleDTO> convertSysRoleDTO(List<SysRole> sysRoleList) {

        if (sysRoleList == null) return null;
        List<SysRoleDTO> sysRoleDTOs = new ArrayList<>();
        for (SysRole sysRole : sysRoleList
                ) {
            SysRoleDTO sysRoleDTO = new SysRoleDTO();
            BeanUtils.copyProperties(sysRole, sysRoleDTO);
            sysRoleDTOs.add(sysRoleDTO);
        }
        return sysRoleDTOs;
    }
}
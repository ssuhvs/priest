package com.little.g.admin.service;

import com.little.g.admin.common.page.Page;
import com.little.g.admin.dto.SysRoleDTO;

import java.util.List;

/**
 * 角色信息表业务层访问接口
 * @author  liuxl
 * @version sysRoleService.java, v 0.1  2017-5-8 9:54:53
 */
public interface SysRoleService
{
	     Long saveSysRole(SysRoleDTO sysRoleDTO);

         Long deleteSysRole(Long id);

         SysRoleDTO querySysRoleById(Long id);

         List<SysRoleDTO> querySysRole(SysRoleDTO sysRoleDTO);

         Page<SysRoleDTO> querySysRolePage(SysRoleDTO sysRoleDTO);
}
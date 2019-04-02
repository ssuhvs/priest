package com.little.g.admin.service;



import com.little.g.admin.common.page.Page;
import com.little.g.admin.dto.SysMenuDTO;

import java.util.List;

/**
 * 菜单信息表业务层访问接口
 *
 * @author liuxl
 * @version sysMenuService.java, v 0.1  2017-5-8 9:54:53
 */
public interface SysMenuService {
    Long saveSysMenu(SysMenuDTO sysMenuDTO);

    Long deleteSysMenu(Long id);

    SysMenuDTO querySysMenuById(Long id);

    List<SysMenuDTO> querySysMenu(SysMenuDTO sysMenuDTO);

    Page<SysMenuDTO> querySysMenuPage(SysMenuDTO sysMenuDTO);

    //按照多个id查询菜单列表
    List<SysMenuDTO> queryMenuByIds(List<Long> menuIds);
}
package com.little.g.admin.service;


import com.little.g.admin.common.page.Page;
import com.little.g.admin.dto.AdminUserDTO;

import java.util.List;

/**
 * 后台管理用户信息表业务层访问接口
 *
 * @author liuxl
 * @version adminUserService.java, v 0.1  2017-5-8 9:54:53
 */
public interface AdminUserService {
    Long saveAdminUser(AdminUserDTO adminUserDTO);

    Long deleteAdminUser(Long id);

    AdminUserDTO queryAdminUserById(Long id);

    List<AdminUserDTO> queryAdminUser(AdminUserDTO adminUserDTO);

    Page<AdminUserDTO> queryAdminUserPage(AdminUserDTO adminUserDTO);

    AdminUserDTO login(String mobile, String password);

    AdminUserDTO findUserByMobile(String mobile);
}
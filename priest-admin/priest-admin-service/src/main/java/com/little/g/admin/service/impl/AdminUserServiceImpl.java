package com.little.g.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.little.g.admin.common.page.Page;
import com.little.g.admin.dto.AdminUserDTO;
import com.little.g.admin.mapper.AdminUserMapper;
import com.little.g.admin.model.AdminUser;
import com.little.g.admin.service.AdminUserService;
import com.little.g.common.encrypt.MD5Utils;
import com.little.g.common.enums.UserStatus;
import com.little.g.common.exception.ServiceDataException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * 后台管理用户信息表业务层访问接口实现
 *
 * @author liuxl
 * @version adminUserServiceImpl.java, v 0.1  2017-5-8 10:07:46
 */
@Service("adminUserService")
public class AdminUserServiceImpl implements AdminUserService {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AdminUserServiceImpl.class);
    /**
     * 后台管理用户信息表数据层访问接口实现
     **/
    @Autowired
    private AdminUserMapper adminUserMapper;

    @Override
    public Long saveAdminUser(AdminUserDTO adminUserDTO) {
        logger.info("saveAdminUser adminUserDTO:{}", adminUserDTO);
        long rows = 0;
        try {
            if (adminUserDTO != null) {
                AdminUser adminUser = new AdminUser();
                BeanUtils.copyProperties(adminUserDTO, adminUser);

                if (adminUser.getId() != null && adminUser.getId() != 0) {
                    //更新
                    adminUser.setUpdateTime(System.currentTimeMillis());
                    if (StringUtils.isNotEmpty(adminUser.getPassword()))
                        adminUser.setPassword(MD5Utils.encode(adminUser.getPassword()));

                    rows = adminUserMapper.updateByPrimaryKeySelective(adminUser);
                } else {
                    //插入
                    adminUser.setPassword(MD5Utils.encode(adminUser.getPassword()));
                    adminUser.setStatus(UserStatus.INIT.getValue());
                    adminUser.setCreateTime(System.currentTimeMillis());
                    rows = adminUserMapper.insert(adminUser);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceDataException("saveAdminUser Error::", e);
        }
        return rows;
    }

    @Override
    public Long deleteAdminUser(Long id) {
        logger.info("deleteAdminUser adminUserId:{}", id);
        long rows = 0;
        try {
            rows = adminUserMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceDataException("deleteadminUser Error::", e);
        }
        return rows;
    }

    @Override
    public AdminUserDTO queryAdminUserById(Long id) {
        logger.info("queryAdminUserById adminUserId:{}", id);
        AdminUserDTO adminUserDTO = null;
        try {
            AdminUser adminUser = adminUserMapper.selectByPrimaryKey(id);
            if (adminUser != null) {
                adminUserDTO = new AdminUserDTO();
                BeanUtils.copyProperties(adminUser, adminUserDTO);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceDataException("queryadminUserById", e);
        }
        return adminUserDTO;
    }

    @Override
    public List<AdminUserDTO> queryAdminUser(AdminUserDTO adminUserDTO) {
        logger.info("queryAdminUser adminUserDTO:{}", adminUserDTO);
        List<AdminUserDTO> list = null;
        List<AdminUser> adminUserList = null;
        try {
            if (adminUserDTO != null) {
                AdminUser adminUser = new AdminUser();
                BeanUtils.copyProperties(adminUserDTO, adminUser);
                adminUserList = adminUserMapper.select(adminUser);
            } else {
                Example example = new Example(AdminUser.class);
                // 排序规则
                example.orderBy("id").desc();
                adminUserList = adminUserMapper.selectByExample(example);
            }
            if (adminUserList != null && adminUserList.size() > 0) {
                list = convertAdminUserDTO(adminUserList);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return list;
    }

    @Override
    public Page<AdminUserDTO> queryAdminUserPage(AdminUserDTO adminUserDTO) {
        logger.info("queryAdminUserPage adminUserDTO:{}", adminUserDTO);
        Page<AdminUserDTO> page = null;
        Integer currentPage = adminUserDTO.getPage();
        if (currentPage < 1) currentPage = 1;
        try {
            page = new Page<AdminUserDTO>(currentPage, adminUserDTO.getPageCount());
            PageHelper.startPage(currentPage, adminUserDTO.getPageCount());
            Example example = new Example(AdminUser.class);

            // 排序规则
            example.orderBy("id").desc();
            List<AdminUser> list = adminUserMapper.selectByExample(example);

            if (list != null && list.size() > 0) {
                PageInfo pageInfo = new PageInfo(list);
                page.setTotalCount(Long.valueOf(pageInfo.getTotal()).intValue());
                List<AdminUserDTO> adminUserDTOList = convertAdminUserDTO(list);
                page.setResult(adminUserDTOList);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return page;
    }

    @Override
    public AdminUserDTO login(String mobile, String password) {
        Example example = new Example(AdminUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("mobile", mobile);
        criteria.andEqualTo("password", DigestUtils.md5Hex(password));
        List<AdminUser> adminUsers = adminUserMapper.selectByExample(example);
        if(adminUsers!=null && adminUsers.size() > 0){
            AdminUser adminUser =adminUsers.get(0);
            if (adminUser != null) {
                AdminUserDTO adminUserDTO = new AdminUserDTO();
                BeanUtils.copyProperties(adminUser,adminUserDTO);
                return adminUserDTO;
            }
        }


        return null;
    }

    @Override
    public AdminUserDTO findUserByMobile(String mobile) {

        Example example = new Example(AdminUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("mobile", mobile);
        List<AdminUser> adminUsers = adminUserMapper.selectByExample(example);
        if(adminUsers!=null && adminUsers.size() > 0){
            AdminUser adminUser =adminUsers.get(0);
            if (adminUser != null) {
                AdminUserDTO adminUserDTO = new AdminUserDTO();
                BeanUtils.copyProperties(adminUser,adminUserDTO);
                return adminUserDTO;
            }
        }
        return null;
    }

    private List<AdminUserDTO> convertAdminUserDTO(List<AdminUser> adminUserList) {

        if (adminUserList == null) return null;
        List<AdminUserDTO> adminUserDTOs = new ArrayList<>();
        for (AdminUser adminUser : adminUserList
                ) {
            AdminUserDTO adminUserDTO = new AdminUserDTO();
            BeanUtils.copyProperties(adminUser, adminUserDTO);
            adminUserDTOs.add(adminUserDTO);
        }
        return adminUserDTOs;
    }
}
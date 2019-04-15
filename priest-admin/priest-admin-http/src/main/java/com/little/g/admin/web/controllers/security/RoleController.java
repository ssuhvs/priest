package com.little.g.admin.web.controllers.security;

import com.little.g.admin.common.annotation.ModuleManage;
import com.little.g.admin.common.annotation.ModuleOperation;
import com.little.g.admin.common.enums.ModuleType;
import com.little.g.admin.common.enums.OperationType;
import com.little.g.common.dto.Page;
import com.little.g.admin.dto.SysMenuDTO;
import com.little.g.admin.dto.SysRoleDTO;
import com.little.g.admin.service.SysMenuService;
import com.little.g.admin.service.SysRoleService;
import com.little.g.admin.web.common.AdminConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@RequestMapping(value = "/admin/role", method = {RequestMethod.GET, RequestMethod.POST})
@ModuleManage(ModuleType.ROLE)
public class RoleController {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Resource
    private SysRoleService roleService;

    @Resource
    private SysMenuService menuService;

    private static Integer pageSize = 15;

    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(HttpServletRequest request, @RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
                       Model view) {
        try {
            SysRoleDTO sysRoleDTO = new SysRoleDTO();
            sysRoleDTO.setPage(currentPage);
            Page<SysRoleDTO> page = roleService.querySysRolePage(sysRoleDTO);

            //放入page对象。
            view.addAttribute("page", page);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "/jsp/role-list";
    }

    @RequestMapping(value = "/edit", method = {RequestMethod.PUT})
    public String edit(@RequestParam(value = "id", required = false, defaultValue = "0") Long id,
                       Model view) {
        try {

            if (id != null && id.longValue() > 0) {
                SysRoleDTO role = roleService.querySysRoleById(id);
                view.addAttribute("role", role);
            }

            List<SysMenuDTO> menuList = menuService.querySysMenu(new SysMenuDTO());
            view.addAttribute("menuList", menuList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "/jsp/role-edit";
    }

    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    @ResponseBody
    @ModuleOperation(value = OperationType.ADD, description = "添加/修改角色")
    public String save(HttpServletRequest request, SysRoleDTO role,
                       Model view) {
        try {

            String[] menuIds = request.getParameterValues("menuId");
            String menus = "";
            if (menuIds != null) {
                for (int i = 0; i < menuIds.length; i++) {
                    menus += menuIds[i];
                    if (i < (menuIds.length - 1)) {
                        menus += ",";
                    }
                }
            }
            role.setMenus(menus);
            long rows = roleService.saveSysRole(role);
            view.addAttribute("menu", role);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return String.format(AdminConstants.WEB_IFRAME_SCRIPT, "保存成功！");
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.DELETE})
    @ResponseBody
    @ModuleOperation(value = OperationType.DELETE, description = "删除角色")
    public String delete(@RequestParam(value = "id", required = false, defaultValue = "0") Long id,
                         Model view) {
        try {

            long rows = roleService.deleteSysRole(id);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return String.format(AdminConstants.WEB_IFRAME_SCRIPT, "删除成功！");
    }
}

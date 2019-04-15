package com.little.g.admin.web.controllers.security;

import com.little.g.admin.common.annotation.ModuleManage;
import com.little.g.admin.common.annotation.ModuleOperation;
import com.little.g.admin.common.enums.ModuleType;
import com.little.g.admin.common.enums.OperationType;
import com.little.g.common.dto.Page;
import com.little.g.admin.dto.AdminUserDTO;
import com.little.g.admin.dto.SysMenuDTO;
import com.little.g.admin.service.SysMenuService;
import com.little.g.admin.web.common.AdminConstants;
import com.little.g.admin.web.util.SessionUtils;
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

import static com.little.g.admin.web.common.AdminConstants.SESSION_USER_KEY;


@Controller
@RequestMapping(value = "/admin/menu", method = {RequestMethod.GET, RequestMethod.POST})
@ModuleManage(ModuleType.MENU)
public class MenuController {
    private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

    @Resource
    private SysMenuService menuService;

    private static Integer pageSize = 15;

    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(HttpServletRequest request, @RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
                       Model view) {
        try {
            SysMenuDTO sysMenuDTO = new SysMenuDTO();
            sysMenuDTO.setPage(currentPage);
            Page<SysMenuDTO> page = menuService.querySysMenuPage(sysMenuDTO);
            //放入page对象。
            view.addAttribute("page", page);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "/jsp/menu-list";
    }

    @RequestMapping(value = "/edit", method = {RequestMethod.PUT})
    public String edit(@RequestParam(value = "id", required = false, defaultValue = "0") Long id,
                       Model view) {
        try {

            if (id != null && id.longValue() > 0) {
                SysMenuDTO menu = menuService.querySysMenuById(id);
                view.addAttribute("menu", menu);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "/jsp/menu-edit";
    }

    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    @ResponseBody
    @ModuleOperation(value = OperationType.ADD, description = "添加/修改资源")
    public String save(HttpServletRequest request, SysMenuDTO menu,
                       Model view) {
        try {
            AdminUserDTO sessionAdminUser = SessionUtils.getSessionValue(request, SESSION_USER_KEY);
            if (sessionAdminUser != null) {
//                menu.setUserId(sessionAdminUser.getId().intValue());
            }
            long rows = menuService.saveSysMenu(menu);
            view.addAttribute("menu", menu);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return String.format(AdminConstants.WEB_IFRAME_SCRIPT, "保存成功！");
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.DELETE})
    @ResponseBody
    @ModuleOperation(value = OperationType.DELETE, description = "删除资源")
    public String delete(@RequestParam(value = "id", required = false, defaultValue = "0") Long id,
                         Model view) {
        try {

            long rows = menuService.deleteSysMenu(id);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return String.format(AdminConstants.WEB_IFRAME_SCRIPT, "删除成功！");
    }
}

package com.little.g.admin.web.controllers.security;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.little.g.admin.common.annotation.ModuleManage;
import com.little.g.admin.common.annotation.ModuleOperation;
import com.little.g.admin.common.enums.ModuleType;
import com.little.g.admin.common.enums.OperationType;
import com.little.g.common.dto.Page;
import com.little.g.admin.dto.AdminUserDTO;
import com.little.g.admin.dto.SysMenuDTO;
import com.little.g.admin.dto.SysRoleDTO;
import com.little.g.admin.service.AdminUserService;
import com.little.g.admin.service.SysMenuService;
import com.little.g.admin.service.SysRoleService;
import com.little.g.admin.web.common.AdminConstants;
import com.little.g.admin.web.util.LuosimaoUtils;
import com.little.g.admin.web.util.SessionUtils;
import com.little.g.common.enums.UserStatus;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.little.g.admin.web.common.AdminConstants.SESSION_USER_KEY;
import static com.little.g.admin.web.common.AdminConstants.SESSION_USER_MENU_KEY;
import static com.little.g.admin.web.common.AdminConstants.SESSION_USER_MENU_MAP_KEY;


@RequestMapping("/admin")
@Controller
@ModuleManage(ModuleType.ADMIN_USER)
public class AdminUserController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(AdminUserController.class);
    @Resource
    private AdminUserService adminUserService;

    @Resource
    private SysRoleService roleService;

    @Autowired
    private SysMenuService menuService;


    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model map, HttpServletRequest request) {
        AdminUserDTO adminUser = SessionUtils.getSessionValue(request, SESSION_USER_KEY);
        if (adminUser != null) {
            map.addAttribute("username", adminUser.getUsername());
        }
        return "/jsp/index";
    }

    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public String login(HttpServletRequest request, Model view) {
        return "/jsp/login";
    }


    @RequestMapping(value = "/login1", method = {RequestMethod.POST})
    public String login1(HttpServletRequest request, String username, String password, String luotest_response, Model view) {

        String profileStatus = AdminConstants.PROFILE_STATUS;
        if (StringUtils.isNotEmpty(profileStatus) && "0".equals(profileStatus)) {
            // 首先验证luosimao鉴权验证码
            if(!LuosimaoUtils.verifyCode(luotest_response)){
                view.addAttribute("msg", "验证码错误！");
                return "/jsp/login";
            }
        }


        AdminUserDTO loginUser = adminUserService.login(username, password);

        if (loginUser != null && Objects.equals(loginUser.getStatus(), UserStatus.INIT.getValue())) {
            SessionUtils.setSession(request, SESSION_USER_KEY, loginUser);
            SysRoleDTO role = roleService.querySysRoleById(loginUser.getRoleId().longValue());
            List<SysMenuDTO> menuList = menuService.queryMenuByIds(role.getMenuList());

            List<SysMenuDTO> pMenuList = convertMenus(menuList);
            SessionUtils.setSession(request, SESSION_USER_MENU_KEY, pMenuList);
            SessionUtils.setSession(request, AdminConstants.SESSION_USER_ROLE_KEY, role.getName());

            List<String> menuUrlMap = Lists.newArrayList(FluentIterable.from(menuList).transform(new Function<SysMenuDTO, String>() {
                @Override
                public String apply(SysMenuDTO sysMenuDTO) {
                    String[] urlPar = sysMenuDTO.getUrl().split("/");
                    if (urlPar.length > 3) {

                        return urlPar[2];
                    }
                    return sysMenuDTO.getUrl();
                }
            }));

            SessionUtils.setSession(request, SESSION_USER_MENU_MAP_KEY, menuUrlMap);

//            String url = "redirect:/admin/lotteryInfo/list";
//            if (menuList != null && menuList.size() > 0) {
//                url = "redirect:" + menuList.get(0).getUrl();
//            }

            return "redirect:/admin/index";
        } else if (loginUser == null) {
            view.addAttribute("msg", "登录失败！用户名/密码错误！");
            return "/jsp/login";
        } else if (loginUser.getStatus() == 1) {
            view.addAttribute("msg", "该用户已被管理员屏蔽");
            return "/jsp/login";
        }

        return null;
    }

    private List<SysMenuDTO> convertMenus(List<SysMenuDTO> sysMenuDTOS) {

        Map<Integer, List<SysMenuDTO>> menuMap = new HashMap<>();
        List<SysMenuDTO> pMenus = new ArrayList<>();
        for (SysMenuDTO sysMenuDTO : sysMenuDTOS) {

            if (sysMenuDTO.getpId() == 0) {
                pMenus.add(sysMenuDTO);
                List<SysMenuDTO> menuList = menuMap.get(sysMenuDTO.getId().intValue());
                if (menuList == null)
                    menuList = new ArrayList<>();
                menuMap.put(sysMenuDTO.getId().intValue(), menuList);
            } else {
                List<SysMenuDTO> menuList = menuMap.get(sysMenuDTO.getpId());
                if (menuList == null)
                    menuList = new ArrayList<>();

                menuList.add(sysMenuDTO);
                menuMap.put(sysMenuDTO.getpId(), menuList);
            }
        }

        for (SysMenuDTO sysMenuDTO : pMenus) {
            List<SysMenuDTO> menuList = menuMap.get(sysMenuDTO.getId().intValue());
            sysMenuDTO.setSubMenus(menuList);

        }


        return pMenus;
    }


    @RequestMapping(value = "/user/edit", method = {RequestMethod.PUT, RequestMethod.GET})
    public String create(HttpServletRequest request, @RequestParam(value = "id", required = false, defaultValue = "0") Long id,
                         Model view) {
        try {

            if (id != null && id.longValue() > 0) {
                AdminUserDTO adminUser = adminUserService.queryAdminUserById(id);
                view.addAttribute("adminUser", adminUser);
            }
            List<SysRoleDTO> roleList = roleService.querySysRole(new SysRoleDTO());

            view.addAttribute("roleList", roleList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "/jsp/user-create";
    }

    @ModuleOperation(value = OperationType.ADD, description = "添加/修改后台用户")
    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    @ResponseBody
    public String createUser(AdminUserDTO user) {
        if (user.getId() != null && user.getId() > 0) {
            if (StringUtils.isEmpty(user.getPassword())){
                user.setPassword(null);
            }
        } else {
            AdminUserDTO adminUser = adminUserService.findUserByMobile(user.getMobile());
            if (adminUser != null)
                return String.format(AdminConstants.WEB_IFRAME_SCRIPT, "手机号码已存在！");
        }
        adminUserService.saveAdminUser(user);
        return String.format(AdminConstants.WEB_IFRAME_SCRIPT, "保存成功！");
    }



    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {

        SessionUtils.cleanSession(request, SESSION_USER_KEY);
        SessionUtils.cleanSession(request, SESSION_USER_MENU_KEY);
        return "redirect:/admin/login";
    }

    @ModuleOperation(value = OperationType.GET, description = "用户列表查询")
    @RequestMapping(value = "/user/list")
    public String adminUserList(@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage, ModelMap map) {
        AdminUserDTO adminUserDTO = new AdminUserDTO();
        adminUserDTO.setPage(currentPage);
        Page<AdminUserDTO> page1 = adminUserService.queryAdminUserPage(adminUserDTO);

        map.put("pagination", page1);
        return "/jsp/user";
    }

    @RequestMapping(value = "/user/resetPassword", method = RequestMethod.GET)
    public String resetPassword(Long id, ModelMap map) {
        map.put("id", id);
        return "/jsp/user-psw";
    }


    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public String updateStatus(Integer id, Integer status) {

        AdminUserDTO adminUser = adminUserService.queryAdminUserById(Long.valueOf(id));
        adminUser.setStatus(status.byteValue());
        adminUserService.saveAdminUser(adminUser);
        return String.format(AdminConstants.WEB_IFRAME_SCRIPT, "修改状态成功！");
    }


    @RequestMapping(value = "/indexContent", method = RequestMethod.GET)
    public String indexContent(){
        return "/jsp/common/content";
    }
}

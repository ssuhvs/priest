package com.little.g.admin.web.interceptor;

import com.little.g.admin.web.common.AdminConstants;
import com.little.g.admin.web.util.SessionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.little.g.admin.web.common.AdminConstants.SESSION_USER_MENU_MAP_KEY;


public class LoginInterceptor extends HandlerInterceptorAdapter {
    private static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession httpSession = SessionUtils.getSession(request);

        logger.info("pre-request:{},uri:{},params:{}", request.getRequestURI(), request.getServletPath(), DateTime.now());
        Object object = httpSession.getAttribute(AdminConstants.SESSION_USER_KEY);
        if (object == null) {
//            throw new ServiceDataException(ErrorCode.ERR_ADMIN_USER_NOT_SIGN_ERROR);
            response.sendRedirect("/admin/login");
            return false;
        } else {
            List<String> menuUrlMap = (List<String>) SessionUtils.getSessionValue(request, SESSION_USER_MENU_MAP_KEY);
            if (menuUrlMap != null) {
                String url = request.getServletPath();
                if(AdminConstants.commonPermission.contains(url)){
                    return true;
                }
                if (StringUtils.isNotEmpty(url)) {
                    String[] urlPar = url.split("/");
                    if (urlPar.length > 3) {

                        //TODO 先做模块权限过滤，下一版再做数据权限
                        if (!menuUrlMap.contains(urlPar[2])) {
                            request.getRequestDispatcher("/WEB-INF/pages/warings.jsp").forward(request, response);
                        }
                    }
                }


            }

        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        super.afterCompletion(request, response, handler, ex);
    }

}

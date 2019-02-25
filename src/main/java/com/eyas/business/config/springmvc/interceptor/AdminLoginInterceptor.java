package com.eyas.business.config.springmvc.interceptor;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: 王龙龙
 * @Date: 2019/2/7 10:22
 * @Description:
 */
public class AdminLoginInterceptor implements HandlerInterceptor {
    public final static String LONGIN_SESSION_NAME = "eyas.website1.session.login";
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getSession().getAttribute(AdminLoginInterceptor.LONGIN_SESSION_NAME)==null||request.getSession().getAttribute(AdminLoginInterceptor.LONGIN_SESSION_NAME).equals("")) {
            if(request.getRequestURI().indexOf("/eyas/admin/pagegoto/index")>-1||request.getRequestURI().indexOf("/eyas/admin/login")>-1) {
                return true;
            }
            response.sendRedirect(request.getContextPath()+"/eyas/admin/pagegoto/index");
            return false;
        }else {
            if(request.getRequestURI().indexOf("/eyas/admin/pagegoto/index")>-1||request.getRequestURI().indexOf("/eyas/admin/login")>-1) {
                response.sendRedirect(request.getContextPath()+"/eyas/admin/index");
                return false;
            }
        }
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }
}

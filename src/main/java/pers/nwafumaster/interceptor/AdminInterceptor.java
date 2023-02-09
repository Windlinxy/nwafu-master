package pers.nwafumaster.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import pers.nwafumaster.annotation.AdminCheck;
import pers.nwafumaster.config.JwtConfig;
import pers.nwafumaster.vo.JsonResult;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Windlinxy
 * @description 管理员权限拦截器
 * @date 2023-02-09 19:14
 **/
@Slf4j(topic = "AdminInterceptor")
public class AdminInterceptor implements HandlerInterceptor {
    @Resource
    private JwtConfig jwtConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(judAdminCheck(handler, request)){
            return true;
        }else {
            JsonResult.returnMsg(response, "需要管理员权限");
            return false;
        }
    }

    /**
     * 检查是否有AdminCheck注释，有则验证是否是管理员
     *
     * @param handler handler
     * @return 是否有PassToken注释
     */
    private boolean judAdminCheck(Object handler, HttpServletRequest request) {
        AdminCheck adminCheck = ((HandlerMethod) handler).getMethodAnnotation(AdminCheck.class);
        if (adminCheck == null){
            adminCheck = ((HandlerMethod) handler).getBean().getClass().getAnnotation(AdminCheck.class);
        }
        String username = jwtConfig.getUser(request.getHeader("Authorization")).getUsername();
        log.info("token-username:{}",username);
        if ("admin".equals(username)) {
            return adminCheck.required();
        } else {
            return false;
        }
    }
}

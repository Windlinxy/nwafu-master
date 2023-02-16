package pers.nwafumaster.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import pers.nwafumaster.annotation.PassToken;
import pers.nwafumaster.config.JwtConfig;
import pers.nwafumaster.vo.JsonResult;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author Windlinxy
 * @description 用户登录权限拦截器
 * @date 2023-01-30 16:05
 **/
@Slf4j(topic = "AuthInterceptor")
public class AuthInterceptor implements HandlerInterceptor {

    @Resource
    private JwtConfig jwtConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        //预检请求方法
        String options = "OPTIONS";
        if (options.equalsIgnoreCase(request.getMethod()) || judPassToken(handler)) {
            return true;
        }
        log.info("options: ==> "+request.getRequestURI());
        if (CorsUtils.isPreFlightRequest(request)){
            return true;
        }
        String uri = request.getRequestURI();
        if (uri.contains("ops")){
            return true;
        }
        String token = request.getHeader("Authorization");
        if (StringUtils.hasLength(token)) {
            if (!jwtConfig.isTokenExpired(token)) {
                request.setAttribute("user",jwtConfig.getUser(token));
                return true;
            }
            JsonResult.returnMsg(response, "token错误");
        } else {
            JsonResult.returnMsg(response, "未登录");
        }
        return false;
    }

    /**
     * 检查是否有PassToken注释，有则跳过认证
     *
     * @param handler handler
     * @return 是否有PassToken注释
     */
    private boolean judPassToken(Object handler) {
        if(handler instanceof HandlerMethod){
            Method method = ((HandlerMethod) handler).getMethod();
            if (method.isAnnotationPresent(PassToken.class)) {
                PassToken passToken = method.getAnnotation(PassToken.class);
                return passToken.required();
            }
        }
        return false;
    }
}

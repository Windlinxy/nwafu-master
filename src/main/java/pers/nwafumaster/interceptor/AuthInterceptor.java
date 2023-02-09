package pers.nwafumaster.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import pers.nwafumaster.annotation.AdminCheck;
import pers.nwafumaster.annotation.PassToken;
import pers.nwafumaster.config.JwtConfig;

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
 * @description 用户登录权限拦截器
 * @date 2023-01-30 16:05
 **/
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
        String token = request.getHeader("Authorization");
        if (StringUtils.hasLength(token)) {
            if (!jwtConfig.isTokenExpired(token)) {
                return true;
            }
            returnMsg(response, "token错误");
        } else {
            returnMsg(response, "未登录");
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



    private void returnMsg(HttpServletResponse response, String msg) throws IOException {

        Map<String, Object> map = new HashMap<>();
        response.setCharacterEncoding("utf-8");
        map.put("code", 1001);
        map.put("msg", msg);
        PrintWriter out;
        String jsonString;
        out = response.getWriter();
        jsonString = new ObjectMapper().writeValueAsString(map);
        out.println(jsonString);
        out.flush();
    }

}

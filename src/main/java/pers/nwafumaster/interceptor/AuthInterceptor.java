package pers.nwafumaster.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
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
 * @description
 * @date 2023-01-30 16:05
 **/
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {
    @Resource
    private JwtConfig jwtConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if (judPassToken(handler)){
            return true;
        }
        String token = request.getHeader("Authorization");
        log.info(token);
        if (token == null) {
            returnCodeNotLogin(response);
            return false;
        } else {
            return !jwtConfig.isTokenExpired(token);
        }
    }

    /**
     * 检查是否有PassToken注释，有则跳过认证
     * @param handler handler
     * @return 是否有PassToken注释
     */
    private boolean judPassToken(Object handler) {
        Method method = ((HandlerMethod) handler).getMethod();
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            return passToken.required();
        }
        return false;
    }

    private void returnCodeNotLogin(HttpServletResponse response) throws IOException {

        Map<String, Object> map = new HashMap<>();
        response.setCharacterEncoding("utf-8");
        map.put("code", 1001);
        map.put("msg", "未登录");
        PrintWriter out;
        String jsonString;
        out = response.getWriter();
        jsonString = new ObjectMapper().writeValueAsString(map);
        out.println(jsonString);
        out.flush();
    }

}

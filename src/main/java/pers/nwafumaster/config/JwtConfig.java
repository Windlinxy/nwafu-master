package pers.nwafumaster.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import pers.nwafumaster.beans.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Windlinxy
 * @description
 * @date 2023-01-30 16:46
 **/


@Slf4j(topic = "jwtConfig")
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    /**
     * 密钥
     */
    private String secret = "NWAFU";

    /**
     * 过期时间（单位：秒）
     **/
    private long expireTime = 604800;


    public String sign(User user) {
        Map<String, Object> claim = new HashMap<>();
        claim.put("username", user.getUsername());
        claim.put("userId", user.getUserId());
        Date date = new Date();
        return Jwts.builder()
                .setClaims(claim)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + expireTime * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 获取token中注册信息
     */
    public Claims getTokenClaim(String token) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
            /*  catch (ExpiredJwtException e){
                    return e.getClaims(); //防止jwt过期解析报错
                }
            */
        }
    }

    /**
     * 验证token是否过期失效
     *
     * @param token 令牌
     */
    public boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        if (expiration != null) {
            return getExpirationDateFromToken(token).before(new Date());
        } else {
            return true;
        }
    }

    /**
     * 获取token失效时间
     *
     * @param token 令牌
     */
    public Date getExpirationDateFromToken(String token) {
        Claims claims = getTokenClaim(token);
        return (claims == null) ? null : claims.getExpiration();
    }

    /**
     * 获取用户名从token中
     */
    public User getUser(String token) {
        Claims map = getTokenClaim(token);
        String username = (String) map.get("username");
        Integer userId = (Integer) map.get("userId");
        return new User(userId, username);
    }

    /**
     * 获取jwt发布时间
     */
    public Date getIssuedAtDateFromToken(String token) {
        return getTokenClaim(token).getIssuedAt();
    }

}

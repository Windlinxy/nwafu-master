package pers.nwafumaster.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.support.DataAccessUtils;
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
@Data
@Component
public class JwtConfig {

    /**
     * 密钥
     */
    @Value("${jwt.key}")
    private String SECRET = "NWAFU";

    /**
     * 过期时间（单位：秒）
     **/
    private final long EXPIRE_TIME = 3600*7*24L;

    public String sign(User user) {
        Map<String, Object> claim = new HashMap<>();
        claim.put(user.getUserId().toString(),user.getUsername());
        Date date = new Date();
        return Jwts.builder()
                .setClaims(claim)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + EXPIRE_TIME * 1000))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    /**
     * 获取token中注册信息
     */
    public Claims getTokenClaim(String token) {
        try {
            return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
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
        return getExpirationDateFromToken(token).before(new Date());
    }

    /**
     * 获取token失效时间
     *
     * @param token 令牌
     */
    public Date getExpirationDateFromToken(String token) {
        return getTokenClaim(token).getExpiration();
    }

    /**
     * 获取用户名从token中
     */
    public String getUsernameFromToken(String token) {
        return getTokenClaim(token).getSubject();
    }

    /**
     * 获取jwt发布时间
     */
    public Date getIssuedAtDateFromToken(String token) {
        return getTokenClaim(token).getIssuedAt();
    }

}

package pers.nwafumaster;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pers.nwafumaster.beans.User;
import pers.nwafumaster.config.JwtConfig;

import java.util.Date;

/**
 * @author Windlinxy
 * @description
 * @date 2023-01-30 17:08
 **/
//@SpringBootTest
//@ActiveProfiles("dev")
public class JWTest {

    @Test
    public void test(){

//        JwtConfig jwtConfig = new JwtConfig();
//        String token = jwtConfig.sign(new User(1,"nihao"));
//        System.out.println(token);
//        System.out.println(jwtConfig.getTokenClaim(token).get("1"));
//        System.out.println(jwtConfig.isTokenExpired(token));
    }
}

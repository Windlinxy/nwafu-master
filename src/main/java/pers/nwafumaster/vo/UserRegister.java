package pers.nwafumaster.vo;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import pers.nwafumaster.beans.UserEntity;

import java.util.List;

/**
 * @author Windlinxy
 * @description
 * @date 2023-01-27 18:55
 **/
@Data
public class UserRegister {
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 感兴趣的问题列表
     */
    @JsonAlias("interestList")
    private List<UserEntity> interestQuestions;


}

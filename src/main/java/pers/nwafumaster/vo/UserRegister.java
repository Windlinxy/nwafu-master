package pers.nwafumaster.vo;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

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
    private int[] interestQuestions;

    public int[] getInterestQuestions() {
        return interestQuestions;
    }

    public void setInterestQuestions(int[] interestQuestions) {
        this.interestQuestions = interestQuestions;
    }


}

package pers.nwafumaster.vo;

import lombok.Data;
import pers.nwafumaster.beans.User;

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
    private Integer[] interestQuestions;

    public Integer[] getInterestQuestions() {
        return interestQuestions;
    }

    public void setInterestQuestions(Integer[] interestQuestions) {
        this.interestQuestions = interestQuestions;
    }


}

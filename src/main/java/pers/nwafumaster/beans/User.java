package pers.nwafumaster.beans;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.nwafumaster.vo.UserRegister;

/**
 * @author Windlinxy
 * @TableName tb_user
 */
@TableName(value = "tb_user")
@Data
@NoArgsConstructor
public class User implements Serializable {
    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    private Integer userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    public User(UserRegister userRegister) {
        this.username = userRegister.getUsername();
        this.password = userRegister.getPassword();
    }

    public User(String username) {
        this.username = username;
    }
}
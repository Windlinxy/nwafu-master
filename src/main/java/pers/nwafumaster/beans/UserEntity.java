package pers.nwafumaster.beans;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Windlinxy
 * @TableName user_entity
 */
@TableName(value ="user_entity")
@Data
@NoArgsConstructor
public class UserEntity implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 实体
     */
    private String entity;

    /**
     * 问题描述
     */
    private String question;

    /**
     * 实体id
     */
    private Integer entityId;

    /**
     * 选择与否
     */
    private Integer mark;

    @TableField(exist = false)
    private Integer questionId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public UserEntity(String username, String entity) {
        this.username = username;
        this.entity = entity;
    }
}
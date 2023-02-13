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
     * 
     */
    private String username;

    /**
     * 
     */
    private String entityName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public UserEntity(String username, String entityName) {
        this.username = username;
        this.entityName = entityName;
    }
}
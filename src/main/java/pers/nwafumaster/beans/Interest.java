package pers.nwafumaster.beans;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName tb_interest
 */
@TableName(value ="tb_interest")
@Data
public class Interest implements Serializable {
    /**
     * 感兴趣id
     */
    @TableId(type = IdType.AUTO)
    private Integer interestedId;

    /**
     * 病害id
     */
    private Integer diseaseId;

    /**
     * 实体
     */
    private String entity;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
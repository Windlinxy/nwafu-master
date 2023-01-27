package pers.nwafumaster.beans;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @author Windlinxy
 * @TableName tb_question
 */
@TableName(value ="tb_question")
@Data
public class Question implements Serializable {
    /**
     * 问题id
     */
    @TableId
    private Integer questionId;

    /**
     * 问题描述
     */
    private String description;

    /**
     * 涉及灾害id
     */
    private Integer diseaseId;

    /**
     * 病虫害名
     */
    private String diseaseName;

    /**
     * 病虫害类型
     */
    private String diseaseType;

    /**
     * 问题类型
     */
    private String questionType;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
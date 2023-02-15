package pers.nwafumaster.beans;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Windlinxy
 * @TableName tb_disease
 */
@TableName(value ="tb_disease")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Disease implements Serializable {
    /**
     * 病虫害id
     */
    @TableId(type = IdType.AUTO)
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
     * 简介
     */
    private String introduce;

    /**
     * 发病症状/形态特征
     */
    private String symptom;

    /**
     * 发病特点/发生特点
     */
    private String features;

    /**
     * 防治措施
     */
    private String control;

    /**
     * 图片url
     */
    private String imgUrl;

    /**
     * 简洁版图片
     */
    @JsonAlias("sImgUrl")
    private String sImgUrl;

    /**
     * 热度
     */
    private Long fire;

    public Disease(Integer diseaseId) {
        this.diseaseId = diseaseId;
    }

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
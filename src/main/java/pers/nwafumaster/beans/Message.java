package pers.nwafumaster.beans;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;
import pers.nwafumaster.enums.MessageType;

/**
 * @TableName tb_message
 */
@TableName(value = "tb_message")
@Data
public class Message implements Serializable {
    /**
     * 留言id
     */
    @TableId(type = IdType.AUTO)
    private Integer messageId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 留言类型
     */
    private MessageType messageType;

    /**
     * 留言标题
     */
    private String title;

    /**
     * 留言内容
     */
    private String content;

    /**
     * 文件url
     */
    private String fileUrl;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
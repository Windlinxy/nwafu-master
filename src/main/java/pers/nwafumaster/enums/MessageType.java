package pers.nwafumaster.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Windlinxy
 * @description
 * @create 2023-01-29 16:14
 **/
public enum MessageType {
    /**
     * 留言类型
     */
    MESSAGE("留言"),
    COMPLAINT("投诉"),
    CONSULT("咨询");

    MessageType(String type) {
        this.type = type;
    }

    /**
     * 类型
     */
    @JsonValue
    private final String type;

    public String getType() {
        return type;
    }
}

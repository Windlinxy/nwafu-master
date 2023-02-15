package pers.nwafumaster.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pers.nwafumaster.beans.UserEntity;

/**
 * @author Windlinxy
 * @description
 * @date 2023-02-13 21:34
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class UserEntityForExc extends BaseRowModel {
    @ExcelProperty(value = "ID", index = 0)
    private Integer id;

    @ExcelProperty(value = "用户名", index = 1)
    private String username;

    @ExcelProperty(value = "问题", index = 2)
    private String question;

    @ExcelProperty(value = "实体", index = 3)
    private String entity;

    @ExcelProperty(value = "实体id", index = 4)
    private Integer entityId;

    @ExcelProperty(value = "mark", index = 5)
    private Integer mark;

    public UserEntityForExc(UserEntity userEntity) {
        this.entity = userEntity.getEntity();
        this.username = userEntity.getUsername();
        this.id = userEntity.getId();
        this.entityId = userEntity.getEntityId();
        this.question = userEntity.getQuestion();
        this.mark = userEntity.getMark();
    }
}

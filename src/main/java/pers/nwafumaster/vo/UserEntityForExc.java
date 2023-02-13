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

    @ExcelProperty(value = "实体", index = 2)
    private String entityName;

    public UserEntityForExc(UserEntity userEntity) {
        this.entityName = userEntity.getEntityName();
        this.username = userEntity.getUsername();
        this.id = userEntity.getId();
    }
}

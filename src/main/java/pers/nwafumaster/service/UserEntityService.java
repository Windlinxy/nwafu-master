package pers.nwafumaster.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.nwafumaster.beans.UserEntity;
import pers.nwafumaster.vo.UserRegister;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author Windlinxy
 */
public interface UserEntityService extends IService<UserEntity> {
    void excelExport(HttpServletResponse response) throws IOException;

    /**
     * 添加用户行文并增加问题热度
     * @param userRegister 注册用户类
     * @return 成功与否
     */
    boolean addEntityUserList(UserRegister userRegister);
}

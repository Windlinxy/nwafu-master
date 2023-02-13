package pers.nwafumaster.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.nwafumaster.beans.UserEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author Windlinxy
 */
public interface UserEntityService extends IService<UserEntity> {
    void excelExport(HttpServletResponse response) throws IOException;
}

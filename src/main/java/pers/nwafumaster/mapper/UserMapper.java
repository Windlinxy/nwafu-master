package pers.nwafumaster.mapper;

import org.apache.ibatis.annotations.Mapper;
import pers.nwafumaster.beans.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Entity pers.nwafumaster.beans.User
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}





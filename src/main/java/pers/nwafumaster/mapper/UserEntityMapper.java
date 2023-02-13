package pers.nwafumaster.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import pers.nwafumaster.beans.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


/**
 * @Entity pers.nwafumaster.beans.UserEntity
 */
@Mapper
public interface UserEntityMapper extends BaseMapper<UserEntity> {



}





package pers.nwafumaster.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import pers.nwafumaster.beans.Interest;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import pers.nwafumaster.beans.Question;

import java.util.List;

/**
 * @author Windlinxy
 * @Entity pers.nwafumaster.beans.Interest
 */
@Mapper
public interface InterestMapper extends BaseMapper<Interest> {

    @Select("SELECT * FROM tb_interest ORDER BY RAND() LIMIT 10;")
    List<Interest> selectListRandomTen();
}





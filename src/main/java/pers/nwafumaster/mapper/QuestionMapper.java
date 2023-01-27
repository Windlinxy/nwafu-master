package pers.nwafumaster.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import pers.nwafumaster.beans.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @Entity generator.domain.TbQuestion
 */
@Mapper
public interface QuestionMapper extends BaseMapper<Question> {
    /**
     * 获取随机10条问题
     * @return 问题列表
     */
    @Select("SELECT * FROM tb_question ORDER BY RAND() LIMIT 10;")
    List<Question> selectListRandomTen();
}





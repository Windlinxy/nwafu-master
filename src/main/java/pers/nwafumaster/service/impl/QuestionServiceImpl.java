package pers.nwafumaster.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import pers.nwafumaster.beans.Question;
import pers.nwafumaster.service.QuestionService;
import pers.nwafumaster.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Windlinxy
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
        implements QuestionService {

    private QuestionMapper questionMapper;

    @Resource
    public void setQuestionMapper(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }


    @Override
    public List<Question> queryRandomTenQuestionList() {
        List<Question> questionList;
        if ((questionList = questionMapper.selectListRandomTen()) != null) {
            return questionList;
        }
        log.warn("未取到问题");
        return null;
    }

}





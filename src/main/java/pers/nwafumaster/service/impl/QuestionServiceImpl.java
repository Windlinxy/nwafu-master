package pers.nwafumaster.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pers.nwafumaster.beans.Disease;
import pers.nwafumaster.beans.Question;
import pers.nwafumaster.beans.UserEntity;
import pers.nwafumaster.mapper.QuestionMapper;
import pers.nwafumaster.service.DiseaseService;
import pers.nwafumaster.service.QuestionService;
import pers.nwafumaster.vo.QuestionAndUrl;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Windlinxy
 */
@Service
@Slf4j(topic = "QuestionService")
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
        implements QuestionService {

    private QuestionMapper questionMapper;

    @Resource
    private DiseaseService diseaseService;

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

    @Override
    public boolean creFire(int questionId) {
        LambdaUpdateWrapper<Question> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Question::getQuestionId, questionId);
        updateWrapper.setSql("fire = fire + 1");
        return update(updateWrapper);
    }

    @Override
    public void batchCreFire(List<UserEntity> list) {
        LambdaUpdateWrapper<Question> updateWrapper;
        for (UserEntity item : list) {
            if(item.getMark() == 1){
                updateWrapper = new LambdaUpdateWrapper<Question>()
                        .eq(Question::getQuestionId, item.getQuestionId())
                        .setSql("fire = fire + 1");
                update(updateWrapper);
            }
        }
    }

    @Override
    public List<QuestionAndUrl> getFireQuestions() {
        LambdaQueryWrapper<Question> queryWrapper = new LambdaQueryWrapper<Question>()
                .select(Question::getDiseaseId, Question::getDescription)
                .orderByDesc(true, Question::getFire)
                .last("limit 4");
        List<Question> list = this.list(queryWrapper);
        List<QuestionAndUrl> result = new ArrayList<>();
        list.forEach(item -> result.add(new QuestionAndUrl(item)));
        result.forEach(
                item -> {
                    LambdaQueryWrapper<Disease> query = new LambdaQueryWrapper<>();
                    query.select(Disease::getImgUrl).eq(Disease::getDiseaseId, item.getDiseaseId());
                    item.setUrl(diseaseService.getObj(query,Object::toString));
                }
        );
        return result;
    }
}





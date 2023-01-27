package pers.nwafumaster.service;

import pers.nwafumaster.beans.Question;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 * @author Windlinxy
 */
public interface QuestionService extends IService<Question> {
    /**
     * 获取10条随机问题
     * @return 问题列表
     */
    List<Question> queryRandomTenQuestionList();

}

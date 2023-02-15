package pers.nwafumaster.service;

import pers.nwafumaster.beans.Question;
import com.baomidou.mybatisplus.extension.service.IService;
import pers.nwafumaster.beans.UserEntity;
import pers.nwafumaster.vo.QuestionAndUrl;

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

    /**
     * 增加题热度
     * @param questionId id
     * @return 是否修改
     */
    boolean creFire(int questionId);

    /**
     * 批量增加热度
     * @param list UserEntityList
     */
    void batchCreFire(List<UserEntity> list);

    /**
     * 获取热门推荐（4条问题）
     * @return 响应
     */
    List<QuestionAndUrl> getFireQuestions();

}

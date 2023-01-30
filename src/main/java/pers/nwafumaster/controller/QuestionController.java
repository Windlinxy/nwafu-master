package pers.nwafumaster.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.nwafumaster.annotation.PassToken;
import pers.nwafumaster.beans.Question;
import pers.nwafumaster.service.QuestionService;
import pers.nwafumaster.vo.JsonResult;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Windlinxy
 * @description 问题相关
 * @date 2023-01-27 19:38
 **/
@RestController
@RequestMapping(value = "/question",
        produces = "application/json"
)
@Slf4j
public class QuestionController {
    private QuestionService questionService;

    @Resource
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("random")
    @PassToken
    public JsonResult<List<Question>> getQuestionsForRegister() {
        List<Question> questionList = questionService.queryRandomTenQuestionList();
        if (questionList != null) {
            return new JsonResult<List<Question>>().ok(questionList);
        }
        return new JsonResult<List<Question>>().fail();
    }
}

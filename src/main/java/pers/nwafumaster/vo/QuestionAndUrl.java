package pers.nwafumaster.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.nwafumaster.beans.Question;

/**
 * @author Windlinxy
 * @description
 * @date 2023-02-15 14:41
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionAndUrl {
    private String question;

    private Integer diseaseId;

    private String url;

    public QuestionAndUrl(Question question) {
        this.question = question.getDescription();
        this.diseaseId = question.getDiseaseId();
    }
}

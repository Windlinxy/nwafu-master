package pers.nwafumaster.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import pers.nwafumaster.beans.Question;

/**
 * @author Windlinxy
 * @description
 * @date 2023-02-15 15:20
 **/
@Data
@NoArgsConstructor
public class EntityForQuestion {
    private String entity;
    private Integer diseaseId;

    public EntityForQuestion(Question question) {
        this.diseaseId = question.getDiseaseId();
        this.entity = question.getEntity();
    }
}

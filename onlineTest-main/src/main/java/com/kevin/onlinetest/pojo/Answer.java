package com.kevin.onlinetest.pojo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * answer
 * @author herokilito
 */
@Data
public class Answer implements Serializable {
    private Integer id;

    private Integer studentId;

    private Integer questionId;

    private String content;

    private Boolean correct;

    private Integer score;

    private Student student;

    private List<Option> answerOptions;

    private static final long serialVersionUID = 1L;
}
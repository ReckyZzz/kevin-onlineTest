package com.kevin.onlinetest.mapper;

import com.kevin.onlinetest.pojo.Answer;

import java.util.List;

/**
 * @author herokilito
 */
public interface AnswerMapper extends FatherMapper<Answer>{

    List<Answer> listAnswerByQuestionId(Integer questionId);

    List<Answer> listAnswerByStudentId(Integer studentId);

    Integer getAnswerNumber(Integer questionId);

    Integer getCurrentNumber(Integer questionId);
}
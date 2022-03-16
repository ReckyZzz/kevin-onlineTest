package com.kevin.onlinetest.mapper;

import com.kevin.onlinetest.pojo.Question;

import java.util.List;

/**
 * @author herokilito
 */
public interface QuestionMapper extends FatherMapper<Question>{

    List<Question> listQuestionsByTeacherId(Integer teacherId);

    List<Question> listQuestionsByCoursesId(Integer courseId);
}
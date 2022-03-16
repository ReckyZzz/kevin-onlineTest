package com.kevin.onlinetest.mapper;

import com.kevin.onlinetest.pojo.AnswerToOption;
import com.kevin.onlinetest.pojo.Option;

import java.util.List;

public interface AnswerToOptionMapper extends FatherMapper<AnswerToOption> {

    List<Option> listOptionByAnswerId(Integer answerId);
}
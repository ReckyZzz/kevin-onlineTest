package com.kevin.onlinetest.mapper;

import com.kevin.onlinetest.pojo.Option;

import java.util.List;

/**
 * @author herokilito
 */
public interface OptionMapper extends FatherMapper<Option>{

    List<Option> listOptionsByQuestionId(Integer questionId);
}
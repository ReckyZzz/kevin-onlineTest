package com.kevin.onlinetest.service;

import com.kevin.onlinetest.pojo.Course;
import com.kevin.onlinetest.pojo.Type;

import java.util.List;

/**
 * @author herokilito
 * @version V1.0
 * @Package com.kevin.onlinetest.service
 * @date 2021/1/2 14:47
 */
public interface QuestionService {

    /**
     * 获取所有的问题类型
     * @return 问题类型列表
     */
    List<Type> getAllTypes();

    /**
     * 获取所有的课程
     * @return 课程列表
     */
    List<Course> getAllCourses();
}

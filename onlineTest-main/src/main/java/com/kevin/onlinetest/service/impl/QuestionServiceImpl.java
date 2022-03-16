package com.kevin.onlinetest.service.impl;

import com.kevin.onlinetest.mapper.CourseMapper;
import com.kevin.onlinetest.mapper.TypeMapper;
import com.kevin.onlinetest.pojo.Course;
import com.kevin.onlinetest.pojo.Type;
import com.kevin.onlinetest.service.QuestionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author herokilito
 * @version V1.0
 * @Package com.kevin.onlinetest.service.impl
 * @date 2021/1/2 14:48
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Resource
    private TypeMapper typeMapper;
    @Resource
    private CourseMapper courseMapper;

    @Override
    public List<Type> getAllTypes() {
        return typeMapper.list();
    }

    @Override
    public List<Course> getAllCourses() {
        return courseMapper.list();
    }
}

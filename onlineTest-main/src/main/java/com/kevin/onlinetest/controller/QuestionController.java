package com.kevin.onlinetest.controller;

import com.kevin.onlinetest.pojo.Course;
import com.kevin.onlinetest.pojo.Type;
import com.kevin.onlinetest.response.CommonResponse;
import com.kevin.onlinetest.service.QuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author herokilito
 * @version V1.0
 * @Package com.kevin.onlinetest.controller
 * @date 2020/12/31 20:56
 */
@RestController
@RequestMapping("/question")
@Api(tags = "问题相关接口")
public class QuestionController {

    @Resource
    private QuestionService questionService;

    @GetMapping("/getAllTypes")
    @ApiOperation("获取所有问题类型")
    CommonResponse<List<Type>> getAllTypes() {
        return new CommonResponse<>(0, "获取成功", questionService.getAllTypes());
    }

    @GetMapping("/getAllCourse")
    @ApiOperation("获取所有问题科目")
    CommonResponse<List<Course>> getAllCourse() {
        return new CommonResponse<>(0, "获取成功", questionService.getAllCourses());
    }
}

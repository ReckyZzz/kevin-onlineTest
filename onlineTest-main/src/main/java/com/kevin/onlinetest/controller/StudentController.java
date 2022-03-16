package com.kevin.onlinetest.controller;

import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageInfo;
import com.kevin.onlinetest.comm.CommonConstants;
import com.kevin.onlinetest.dos.AnswerDO;
import com.kevin.onlinetest.pojo.Question;
import com.kevin.onlinetest.pojo.Student;
import com.kevin.onlinetest.response.CommonResponse;
import com.kevin.onlinetest.service.StudentService;
import com.kevin.onlinetest.util.SessionUtil;
import com.kevin.onlinetest.vo.AnswerHistoryVO;
import com.kevin.onlinetest.vo.TestResultVO;
import com.kevin.onlinetest.vo.TestVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author herokilito
 */
@Api(tags = "学生相关接口")
@RestController
@RequestMapping("/student")
public class StudentController {

    @Resource
    private StudentService studentService;

    @GetMapping("/isLogin")
    @ApiOperation("学生是否登录")
    public CommonResponse<Object> isLogin(@ApiIgnore HttpSession session) {
        if (session.getAttribute(CommonConstants.STUDENT_SESSION_NAME) != null) {
            return new CommonResponse<>(0, "已登录", null);
        } else {
            return new CommonResponse<>(1, "未登录", null);
        }
    }

    @PostMapping("/login")
    @ApiOperation("学生登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentNum", value = "学生学号", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true)
    })
    public CommonResponse<Student> login(@RequestParam Integer studentNum,
                                         @RequestParam String password,
                                         @ApiIgnore HttpSession session) {
        Student student = new Student();
        student.setStuNum(studentNum);
        student.setPassword(password);
        SessionUtil.setSession(session);
        return studentService.login(student);
    }

    @PostMapping("/logout")
    @ApiOperation("学生登出")
    public CommonResponse<String> logout(@ApiIgnore HttpSession session) {
        session.setAttribute(CommonConstants.STUDENT_SESSION_NAME, null);
        return new CommonResponse<>(0, "成功登出", null);
    }

    @PostMapping("/updatePwd")
    @ApiOperation("修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPwd", value = "原密码", required = true),
            @ApiImplicitParam(name = "newPwd", value = "新密码", required = true)
    })
    public CommonResponse<Object> updatePwd(@RequestParam String oldPwd, @RequestParam String newPwd, @ApiIgnore HttpSession session) {
        Student student = (Student) session.getAttribute(CommonConstants.STUDENT_SESSION_NAME);
        if (studentService.updatePassword(student, oldPwd, newPwd)) {
            session.removeAttribute(CommonConstants.STUDENT_SESSION_NAME);
            return new CommonResponse<>(0, "修改成功", null);
        }
        return new CommonResponse<>(1, "原密码错误", null);
    }

    @GetMapping("/getTest")
    @ApiOperation("获取一个测试")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "课程id", required = true),
            @ApiImplicitParam(name = "questionCount", value = "题量", required = true)
    })
    public CommonResponse<TestVO> getTest(@RequestParam Integer courseId,
                                          @RequestParam Integer questionCount,
                                          @ApiIgnore HttpSession session) {
        List<Question> questionList = studentService.getQuestions(courseId, questionCount);
        TestVO test = new TestVO();
        session.setAttribute(CommonConstants.TEST_SESSION_NAME, questionList);
        test.setQuestionList(questionList);
        test.setQuestionCount(questionList.size());
        return new CommonResponse<>(0, "获取成功", test);
    }

    @PutMapping("/submitTest")
    @ApiOperation("提交测试结果")
    public CommonResponse<TestResultVO> submitTest(@RequestParam String answerJson, @ApiIgnore HttpSession session) {
        List<AnswerDO> answers = JSONUtil.toList(JSONUtil.parseArray(answerJson), AnswerDO.class);
        Student student = (Student) session.getAttribute(CommonConstants.STUDENT_SESSION_NAME);
        List<Question> questionList = (List<Question>) session.getAttribute(CommonConstants.TEST_SESSION_NAME);
        return new CommonResponse<>(0 , "测试结果",
                studentService.completeTest(student.getId(), questionList, answers));
    }

    @GetMapping("/listHistory")
    @ApiOperation("查看回答历史")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页号", required = true),
            @ApiImplicitParam(name = "pageSize", value = "一页的记录数", required = true)
    })
    public CommonResponse<PageInfo<AnswerHistoryVO>> listHistory(@RequestParam Integer pageNum,
                                                                 @RequestParam Integer pageSize,
                                                                 @ApiIgnore HttpSession session) {
        Student student = (Student) session.getAttribute(CommonConstants.STUDENT_SESSION_NAME);
        return new CommonResponse<>(0, "获取成功", studentService.listAnswerHistory(student.getId(), pageNum, pageSize));
    }
}

package com.kevin.onlinetest.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageInfo;
import com.kevin.onlinetest.comm.CommonConstants;
import com.kevin.onlinetest.pojo.Answer;
import com.kevin.onlinetest.pojo.Question;
import com.kevin.onlinetest.pojo.Teacher;
import com.kevin.onlinetest.response.CommonResponse;
import com.kevin.onlinetest.service.TeacherService;
import com.kevin.onlinetest.util.SessionUtil;
import com.kevin.onlinetest.vo.QuestionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author herokilito
 * @version V1.0
 * @Package com.kevin.onlinetest.controller
 * @date 2020/11/4 9:42
 */
@Api(tags = "教师相关接口")
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Resource
    private TeacherService teacherService;

    @GetMapping("/isLogin")
    @ApiOperation("教师是否登录")
    public CommonResponse<Object> isLogin(@ApiIgnore HttpSession session) {
        if (session.getAttribute(CommonConstants.TEACHER_SESSION_NAME) != null) {
            return new CommonResponse<>(0, "已登录", null);
        } else {
            return new CommonResponse<>(1, "未登录", null);
        }
    }

    @PostMapping("/login")
    @ApiOperation("教师登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherNum", value = "教师职工号", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true)
    })
    public CommonResponse<Teacher> login(@RequestParam Integer teacherNum,
                                         @RequestParam String password,
                                         @ApiIgnore HttpSession session) {
        Teacher teacher = new Teacher();
        teacher.setTeacherNum(teacherNum);
        teacher.setPassword(password);
        SessionUtil.setSession(session);
        return teacherService.login(teacher);
    }

    @PostMapping("/logout")
    @ApiOperation("教师登出")
    public CommonResponse<String> logout(@ApiIgnore HttpSession session) {
        session.setAttribute(CommonConstants.TEACHER_SESSION_NAME, null);
        return new CommonResponse<>(0, "成功登出", null);
    }

    @PostMapping("/updatePwd")
    @ApiOperation("修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPwd", value = "原密码", required = true),
            @ApiImplicitParam(name = "newPwd", value = "新密码", required = true)
    })
    public CommonResponse<Object> updatePwd(@RequestParam String oldPwd, @RequestParam String newPwd, @ApiIgnore HttpSession session) {
        Teacher teacher = (Teacher) session.getAttribute(CommonConstants.TEACHER_SESSION_NAME);
        if (teacherService.updatePassword(teacher, oldPwd, newPwd)) {
            session.removeAttribute(CommonConstants.TEACHER_SESSION_NAME);
            return new CommonResponse<>(0, "修改成功", null);
        }
        return new CommonResponse<>(1, "原密码错误", null);
    }

    @PutMapping("/addQuestion")
    @ApiOperation("添加问题")
    @ApiImplicitParam(name = "questionJson", value = "问题的json格式数据", required = true)
    public CommonResponse<Question> addQuestion(@RequestParam String questionJson, @ApiIgnore HttpSession session) {
        JSONObject questionObj = JSONUtil.parseObj(questionJson);
        Question questionInfo = JSONUtil.toBean(questionObj, Question.class);
        questionInfo.setTeacherId(((Teacher)session.getAttribute(CommonConstants.TEACHER_SESSION_NAME)).getId());
        Question question = teacherService.addQuestion(questionInfo);
        if (question != null) {
            return new CommonResponse<>(0, "添加成功", question);
        }
        return null;
    }

    @GetMapping("/listQuestions")
    @ApiOperation("获取教师自己发布的问题列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页号", required = true),
            @ApiImplicitParam(name = "pageSize", value = "一页的记录数", required = true)
    })
    public CommonResponse<PageInfo<Question>> listQuestions(@RequestParam Integer pageNum,
                                                            @RequestParam Integer pageSize,
                                                            @ApiIgnore HttpSession session) {
        Teacher teacher = (Teacher) session.getAttribute(CommonConstants.TEACHER_SESSION_NAME);
        return new CommonResponse<>(0, "获取成功", teacherService.listQuestions(pageNum, pageSize, teacher));
    }

    @GetMapping("/listAllQuestions")
    @ApiOperation("获取所有问题列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页号", required = true),
            @ApiImplicitParam(name = "pageSize", value = "一页的记录数", required = true)
    })
    public CommonResponse<PageInfo<Question>> listQuestion(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return new CommonResponse<>(0, "获取成功", teacherService.listAllQuestions(pageNum, pageSize));
    }

    @GetMapping("/getQuestion")
    @ApiOperation("根据id获取问题信息")
    @ApiImplicitParam(name = "id", value = "问题的id", required = true)
    public CommonResponse<QuestionVO> getQuestion(@RequestParam Integer id) {
        QuestionVO questionVO = teacherService.getQuestion(id);
        if (questionVO != null) {
            return new CommonResponse<>(0, "获取成功", questionVO);
        } else {
            return new CommonResponse<>(0, "获取失败", null);
        }
    }

    @PostMapping("/updateQuestion")
    @ApiOperation("教师修改问题")
    @ApiImplicitParam(name = "questionJson", value = "问题的json字符串", required = true)
    public CommonResponse<Question> updateQuestion(@RequestParam String questionJson) {
        JSONObject questionObj = JSONUtil.parseObj(questionJson);
        Question questionInfo = JSONUtil.toBean(questionObj, Question.class);
        Question question = teacherService.updateQuestion(questionInfo);
        if (question != null) {
            return new CommonResponse<>(0, "更新成功", question);
        }
        return new CommonResponse<>(1, "更新失败", null);
    }

    @DeleteMapping("/delQuestion")
    @ApiOperation("教师删除问题")
    @ApiImplicitParam(name = "id", value = "问题id", required = true)
    public CommonResponse<Object> delQuestion(@RequestParam Integer id) {
        teacherService.deleteQuestion(id);
        return new CommonResponse<>(0, "删除成功", null);
    }

    @GetMapping("/getAnswerList")
    @ApiOperation("根据问题id获取回答列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionId", value = "问题id", required = true),
            @ApiImplicitParam(name = "pageNum", value = "页号", required = true),
            @ApiImplicitParam(name = "pageSize", value = "一页的记录数", required = true)
    })
    public CommonResponse<PageInfo<Answer>> getAnswerList(@RequestParam Integer questionId,
                                                          @RequestParam Integer pageNum,
                                                          @RequestParam Integer pageSize) {
        return new CommonResponse<>(0, "获取成功",
                teacherService.getAnswerList(questionId, pageNum, pageSize));
    }
}

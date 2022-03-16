package com.kevin.onlinetest.controller;

import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageInfo;
import com.kevin.onlinetest.comm.CommonConstants;
import com.kevin.onlinetest.pojo.*;
import com.kevin.onlinetest.service.AdminService;
import com.kevin.onlinetest.util.SessionUtil;
import com.kevin.onlinetest.response.CommonResponse;
import io.swagger.annotations.*;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author herokilito
 */
@Api(tags = "管理员相关的接口")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    @GetMapping("/isLogin")
    @ApiOperation("管理员是否登录")
    public CommonResponse<Object> isLogin(@ApiIgnore HttpSession session) {
        if (session.getAttribute(CommonConstants.ADMIN_SESSION_NAME) != null) {
            return new CommonResponse<>(0, "已登录", null);
        } else {
            return new CommonResponse<>(1, "未登录", null);
        }
    }

    @PostMapping("/login")
    @ApiOperation("管理员登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "管理员账号", required = true),
            @ApiImplicitParam(name = "password", value = "管理员密码", required = true)
    })
    public CommonResponse<Admin> login(@RequestParam String account,
                                       @RequestParam String password,
                                       @ApiIgnore HttpSession session) {
        Admin admin = new Admin();
        admin.setAccount(account);
        admin.setPassword(password);
        SessionUtil.setSession(session);
        return adminService.login(admin);
    }

    @PostMapping("/logout")
    @ApiOperation("管理员登出")
    public CommonResponse<String> logout(@ApiIgnore HttpSession session) {
        session.setAttribute(CommonConstants.ADMIN_SESSION_NAME, null);
        return new CommonResponse<>(0, "成功登出", null);
    }

    @GetMapping("/getStudentInfo")
    @ApiOperation("获取学生列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页号", required = true),
            @ApiImplicitParam(name = "pageSize", value = "一页的记录数", required = true)
    })
    public CommonResponse<PageInfo<Student>> getStudentInfo(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return adminService.getStudentInfo(pageNum, pageSize);
    }

    @GetMapping("/getStudentById")
    @ApiOperation("根据学号获取学生信息")
    @ApiImplicitParam(name = "stuNum", value = "学生id", required = true)
    public CommonResponse<Student> getStudentById(@RequestParam Integer stuNum) {
        Student student = adminService.getStudentByNum(stuNum);
        return student != null
                ? new CommonResponse<>(0, "查询成功", student)
                : new CommonResponse<>(1, "学号不存在", null);
    }

    @DeleteMapping("/deleteStudents")
    @ApiOperation("批量删除学生")
    @ApiImplicitParam(name = "studentIds", value = "学生id数组（json格式）", required = true, dataType = "string")
    public CommonResponse<Object> deleteStudents(@RequestParam String studentIds) {
        List<Integer> ids = JSONUtil.toList(JSONUtil.parseArray(studentIds), Integer.class);
        adminService.deleteStudents(ids);
        return new CommonResponse<>(0, "删除成功", null);
    }

    @PostMapping("/updateStudent")
    @ApiOperation("更新学生信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "学生id", required = true),
    })
    public CommonResponse<Student> updateStudent(Student student) {
        student = adminService.updateStudent(student);
        if (student != null) {
            return new CommonResponse<>(0, "更新成功", student);
        }
        return new CommonResponse<>(1, "不存在的id", null);
    }


    @PostMapping("/restStudentPwd")
    @ApiOperation("重置学生密码")
    @ApiImplicitParam(name = "id", value = "学生id", required = true)
    public CommonResponse<Object> resetStudentPwd(@RequestParam Integer id) {
        adminService.restStudentPwd(id);
        return new CommonResponse<>(0, "重置成功", null);
    }

    @PutMapping("/addStudent")
    @ApiOperation("添加学生")
    public CommonResponse<Student> addStudent(Student student) {
        student = adminService.addStudent(student);
        if (student == null) {
            return new CommonResponse<>(1, "学号已存在", null);
        }
        return new CommonResponse<>(0, "添加成功", student);
    }

    @GetMapping("/getTeacherInfo")
    @ApiOperation("获取教师列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页号", required = true),
            @ApiImplicitParam(name = "pageSize", value = "一页的记录数", required = true)
    })
    public CommonResponse<PageInfo<Teacher>> getTeacherInfo(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return adminService.getTeacherInfo(pageNum, pageSize);
    }

    @GetMapping("/getTeacherById")
    @ApiOperation("根据教工号获取教师信息")
    @ApiImplicitParam(name = "teacherNum", value = "教师工号", required = true)
    public CommonResponse<Teacher> getTeacherById(@RequestParam Integer teacherNum) {
        Teacher teacher = adminService.getTeacherByNum(teacherNum);
        return teacher != null
                ? new CommonResponse<>(0, "查询成功", teacher)
                : new CommonResponse<>(1, "工号不存在", null);
    }

    @DeleteMapping("/deleteTeachers")
    @ApiOperation("批量删除教师")
    @ApiImplicitParam(name = "teacherIds", value = "教师id数组（json格式）", required = true, dataType = "string")
    public CommonResponse<Object> deleteTeachers(@RequestParam String teacherIds) {
        List<Integer> ids = JSONUtil.toList(JSONUtil.parseArray(teacherIds), Integer.class);
        adminService.deleteTeachers(ids);
        return new CommonResponse<>(0, "删除成功", null);
    }

    @PostMapping("/updateTeacher")
    @ApiOperation("更新教师信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "教师id", required = true),
    })
    public CommonResponse<Teacher> updateTeacher(Teacher teacher) {
        teacher = adminService.updateTeacher(teacher);
        if (teacher != null) {
            return new CommonResponse<>(0, "更新成功", teacher);
        }
        return new CommonResponse<>(1, "不存在的id", null);
    }

    @PostMapping("/restTeacherPwd")
    @ApiOperation("重置教师密码")
    @ApiImplicitParam(name = "id", value = "教师id", required = true)
    public CommonResponse<Object> resetTeacherPwd(@RequestParam Integer id) {
        adminService.restTeacherPwd(id);
        return new CommonResponse<>(0, "重置成功", null);
    }

    @PutMapping("/addTeacher")
    @ApiOperation("添加教师")
    public CommonResponse<Teacher> addTeacher(Teacher teacher) {
        teacher = adminService.addTeacher(teacher);
        if (teacher == null) {
            return new CommonResponse<>(1, "职工号已存在", null);
        }
        return new CommonResponse<>(0, "添加成功", teacher);
    }

    @GetMapping("/getQuestion")
    @ApiOperation("获取问题信息")
    @ApiImplicitParam(name = "id", value = "问题id", required = true)
    public CommonResponse<Question> getQuestion(@RequestParam Integer id) {
        Question question = adminService.getQuestionById(id);
        if (question != null) {
            return new CommonResponse<>(0, "获取成功", question);
        }
        return new CommonResponse<>(1, "问题不存在", null);
    }

    @GetMapping("/listQuestion")
    @ApiOperation("获取问题列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页号", required = true),
            @ApiImplicitParam(name = "pageSize", value = "一页的记录数", required = true)
    })
    public CommonResponse<PageInfo<Question>> listQuestion(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return new CommonResponse<>(0, "获取成功", adminService.listQuestions(pageNum, pageSize));
    }


    @GetMapping("/getAnswer")
    @ApiOperation("获取回答信息")
    @ApiImplicitParam(name = "id", value = "回答id", required = true)
    public CommonResponse<Answer> getAnswer(@RequestParam Integer id) {
        Answer answer = adminService.getAnswerById(id);
        if (answer != null) {
            return new CommonResponse<>(0, "获取成功", adminService.getAnswerById(id));
        }
        return new CommonResponse<>(1, "回答不存在", null);
    }

    @GetMapping("/listAnswer")
    @ApiOperation("获取回答列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页号", required = true),
            @ApiImplicitParam(name = "pageSize", value = "一页的记录数", required = true)
    })
    public CommonResponse<PageInfo<Answer>> listAnswer(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return new CommonResponse<>(0, "获取成功", adminService.listAnswers(pageNum, pageSize));
    }

    @DeleteMapping("/deleteQuestions")
    @ApiOperation("批量删除问题")
    @ApiImplicitParam(name = "questionIds", value = "教师id数组（json格式）", required = true, dataType = "string")
    public CommonResponse<Object> deleteQuestions(@RequestParam String questionIds) {
        List<Integer> ids = JSONUtil.toList(JSONUtil.parseArray(questionIds), Integer.class);
        adminService.deleteQuestions(ids);
        return new CommonResponse<>(0, "删除成功", null);
    }

    @GetMapping("/listCourse")
    @ApiOperation("获取课程列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页号", required = true),
            @ApiImplicitParam(name = "pageSize", value = "一页的记录数", required = true)
    })
    public CommonResponse<PageInfo<Course>> listCourse(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return new CommonResponse<>(0, "获取成功", adminService.listCourse(pageNum, pageSize));
    }

    @GetMapping("/getCourse")
    @ApiOperation("根据id获取课程")
    @ApiImplicitParam(name = "courseId", value = "课程id", required = true)
    public CommonResponse<Course> getCourse(@RequestParam Integer courseId) {
        Course course = adminService.getCourse(courseId);
        if (course != null) {
            return new CommonResponse<>(0, "获取成功", course);
        }
        return new CommonResponse<>(1, "获取失败", null);
    }

    @DeleteMapping("/deleteCourse")
    @ApiOperation("批量删除课程")
    @ApiImplicitParam(name = "courseIds", value = "课程id数组（json格式）", required = true, dataType = "string")
    public CommonResponse<Object> deleteCourse(@RequestParam String courseIds) {
        List<Integer> ids = JSONUtil.toList(JSONUtil.parseArray(courseIds), Integer.class);
        adminService.deleteCourse(ids);
        return new CommonResponse<>(0, "删除成功", null);
    }

    @PutMapping("/addCourse")
    @ApiOperation("新增课程")
    public CommonResponse<Course> addCourse(Course course) {
        return new CommonResponse<>(0, "新增成功", adminService.addCourse(course));
    }

    @PostMapping("/updateCourse")
    @ApiOperation("更新课程信息")
    public CommonResponse<Course> updateCourse(Course course) {
        return new CommonResponse<>(0, "更新成功", adminService.updateCourse(course));
    }
}

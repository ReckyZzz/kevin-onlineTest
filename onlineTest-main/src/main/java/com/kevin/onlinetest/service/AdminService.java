package com.kevin.onlinetest.service;

import com.github.pagehelper.PageInfo;
import com.kevin.onlinetest.pojo.*;
import com.kevin.onlinetest.response.CommonResponse;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * @author herokilito
 */
public interface AdminService extends BaseService<Admin> {

    /**
     * 管理员登录
     * @param admin 管理员登录信息
     * @return 响应json
     */
    CommonResponse<Admin> login(Admin admin);

    /**
     * 获取学生信息列表
     * @param pageNum 页码
     * @param pageSize 页大小
     * @return 响应json
     */
    CommonResponse<PageInfo<Student>> getStudentInfo(Integer pageNum, Integer pageSize);

    /**
     * 获取教师信息列表
     * @param pageNum 页码
     * @param pageSize 页大小
     * @return 响应json
     */
    CommonResponse<PageInfo<Teacher>> getTeacherInfo(Integer pageNum, Integer pageSize);

    /**
     * 批量删除教师
     * @param teacherIds 教师的id列表
     */
    void deleteTeachers(List<Integer> teacherIds);

    /**
     * 批量删除学生
     * @param studentIds 学生的id列表
     */
    void deleteStudents(List<Integer> studentIds);

    /**
     * 根据id获取学生信息
     * @param id id
     * @return 学生信息
     */
    Student getStudentByNum(Integer id);

    /**
     * 根据id获取教师信息
     * @param id id
     * @return 教师信息
     */
    Teacher getTeacherByNum(Integer id);

    /**
     * 更新学生信息
     * @param student 学生信息
     * @return 更新后的信息
     */
    Student updateStudent(Student student);

    /**
     * 更新教师信息
     * @param teacher 教师信息
     * @return 更新后的信息
     */
    Teacher updateTeacher(Teacher teacher);

    /**
     * 根据id获取问题
     * @param id 问题的id
     * @return 问题
     */
    Question getQuestionById(Integer id);

    /**
     * 根据id获取回答
     * @param id 回答的id
     * @return 回答
     */
    Answer getAnswerById(Integer id);

    /**
     * 获取问题信息列表
     * @param pageNum 页码
     * @param pageSize 页大小
     * @return 列表
     */
    PageInfo<Question> listQuestions(Integer pageNum, Integer pageSize);

    /**
     * 获取回答信息列表
     * @param pageNum 页码
     * @param pageSize 页大小
     * @return 列表
     */
    PageInfo<Answer> listAnswers(Integer pageNum, Integer pageSize);

    /**
     *  重置学生密码
     * @param id 学生id
     */
    void restStudentPwd(Integer id);

    /**
     *  重置教师密码
     * @param id 教师id
     */
    void restTeacherPwd(Integer id);

    /**
     * 添加教师
     * @param teacher 教师
     * @return 教师
     */
    Teacher addTeacher(Teacher teacher);

    /**
     * 添加学生
     * @param student 学生
     * @return 学生
     */
    Student addStudent(Student student);

    /**
     * 批量删除问题
     * @param questionIds 学生的id列表
     */
    void deleteQuestions(List<Integer> questionIds);

    PageInfo<Course> listCourse(Integer pageNum, Integer pageSize);

    Course getCourse(Integer courseId);

    void deleteCourse(List<Integer> courseIds);

    Course addCourse(Course course);

    Course updateCourse(Course course);
}

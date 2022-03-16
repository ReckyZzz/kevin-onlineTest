package com.kevin.onlinetest.service;

import com.github.pagehelper.PageInfo;
import com.kevin.onlinetest.pojo.Answer;
import com.kevin.onlinetest.pojo.Question;
import com.kevin.onlinetest.pojo.Teacher;
import com.kevin.onlinetest.response.CommonResponse;
import com.kevin.onlinetest.vo.QuestionVO;

/**
 * @author herokilito
 * @version V1.0
 * @Package com.kevin.onlinetest.service
 * @date 2020/12/31 19:40
 */
public interface TeacherService extends BaseService<Teacher> {

    /**
     * 教师登录
     * @param teacher 教师信息
     * @return 教师信息
     */
    CommonResponse<Teacher> login(Teacher teacher);

    /**
     * 修改密码
     * @param teacher 教师
     * @param newPassword 新密码
     * @return 修改成功与否
     */
    boolean updatePassword(Teacher teacher, String oldPassword, String newPassword);

    /**
     * 添加问题
     * @param question 问题
     * @return 问题
     */
    Question addQuestion(Question question);

    /**
     * 获取教师发布的问题列表
     * @param pageNum 页号
     * @param pageSize 一页大小
     * @param teacher 教师
     * @return 列表
     */
    PageInfo<Question> listQuestions(Integer pageNum, Integer pageSize, Teacher teacher);

    /**
     * 获取问题信息列表
     * @param pageNum 页码
     * @param pageSize 页大小
     * @return 列表
     */
    PageInfo<Question> listAllQuestions(Integer pageNum, Integer pageSize);

    /**
     * 根据id获取问题详情信息
     * @param id 问题id
     * @return 问题
     */
    QuestionVO getQuestion(Integer id);

    /**
     * 更新问题
     * @param question 更新前的问题
     * @return 更新后的问题
     */
    Question updateQuestion(Question question);

    /**
     * 删除问题
     * @param id 问题的id
     */
    void deleteQuestion(Integer id);

    /**\
     * 获取回答列表
     * @param questionId 问题id
     * @param pageNum 页码
     * @param pageSize 一页大小
     * @return 回答列表
     */
    PageInfo<Answer> getAnswerList(Integer questionId, Integer pageNum, Integer pageSize);
}

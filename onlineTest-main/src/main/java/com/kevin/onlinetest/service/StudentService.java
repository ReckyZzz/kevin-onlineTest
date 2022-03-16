package com.kevin.onlinetest.service;

import com.github.pagehelper.PageInfo;
import com.kevin.onlinetest.dos.AnswerDO;
import com.kevin.onlinetest.pojo.Answer;
import com.kevin.onlinetest.pojo.Question;
import com.kevin.onlinetest.pojo.Student;
import com.kevin.onlinetest.pojo.Teacher;
import com.kevin.onlinetest.response.CommonResponse;
import com.kevin.onlinetest.vo.AnswerHistoryVO;
import com.kevin.onlinetest.vo.TestResultVO;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * @author herokilito
 * @version V1.0
 * @Package com.kevin.onlinetest.service
 * @date 2021/1/3 18:13
 */
public interface StudentService {

    /**
     * 登录
     * @param student 学生
     * @return 结果
     */
    CommonResponse<Student> login(Student student);

    /**
     * 修改密码
     * @param student 学生信息
     * @param newPassword 新密码
     * @return 修改成功与否
     */
    boolean updatePassword(Student student, String oldPassword, String newPassword);

    /**
     * 根据id获取问题信息
     * @param id id
     * @return 问题
     */
    Question getQuestionById(Integer id);

    /**
     * 获取问题
     * @param courseId 课程id
     * @param count 获取数量
     * @return 问题列表
     */
    List<Question> getQuestions(Integer courseId, Integer count);

    /**
     * 回答主观题
     * @param optionIds 选择的选项
     * @return 回答
     */
    Answer answerObjectiveQuestion(List<Integer> optionIds);

    /**
     * 完成测试计算结果
     * @param answers 回答内容
     * @return 测试结果
     */
    TestResultVO completeTest (Integer studentId, List<Question> questions, List<AnswerDO> answers);

    PageInfo<AnswerHistoryVO> listAnswerHistory(Integer studentId, Integer pageNum, Integer pageSize);
}

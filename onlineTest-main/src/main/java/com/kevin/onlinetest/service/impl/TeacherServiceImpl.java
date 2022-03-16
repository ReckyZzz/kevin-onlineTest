package com.kevin.onlinetest.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kevin.onlinetest.comm.CommonConstants;
import com.kevin.onlinetest.mapper.AnswerMapper;
import com.kevin.onlinetest.mapper.OptionMapper;
import com.kevin.onlinetest.mapper.QuestionMapper;
import com.kevin.onlinetest.mapper.TeacherMapper;
import com.kevin.onlinetest.pojo.Answer;
import com.kevin.onlinetest.pojo.Option;
import com.kevin.onlinetest.pojo.Question;
import com.kevin.onlinetest.pojo.Teacher;
import com.kevin.onlinetest.response.CommonResponse;
import com.kevin.onlinetest.service.TeacherService;
import com.kevin.onlinetest.util.SecurityUtil;
import com.kevin.onlinetest.util.SessionUtil;
import com.kevin.onlinetest.vo.QuestionVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author herokilito
 * @version V1.0
 * @Package com.kevin.onlinetest.service.impl
 * @date 2020/12/31 19:52
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    @Resource
    private TeacherMapper teacherMapper;
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private AnswerMapper answerMapper;
    @Resource
    private OptionMapper optionMapper;

    @Override
    public Teacher get(Integer id) {
        return null;
    }

    @Override
    public List<Teacher> list() {
        return null;
    }

    @Override
    public void insert(Teacher object) {

    }

    @Override
    public void update(Teacher object) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public CommonResponse<Teacher> login(Teacher teacher) {
        HttpSession session = SessionUtil.getSession();
        SessionUtil.removeSession();
        Teacher teacherInfo = teacherMapper.get(teacher.getTeacherNum());
        if (teacherInfo != null) {
            if (SecurityUtil.check(teacher.getPassword(), teacherInfo.getPassword())) {
                if (session != null) {
                    session.setAttribute(CommonConstants.TEACHER_SESSION_NAME, teacherInfo);
                }
                return new CommonResponse<>(0, "登录成功", teacherInfo);
            } else {
                return new CommonResponse<>(2, "用户名或密码错误", null);
            }
        } else {
            return new CommonResponse<>(2, "用户名或密码错误", null);
        }
    }

    @Override
    public boolean updatePassword(Teacher teacher, String oldPassword, String newPassword) {
        oldPassword = SecurityUtil.getEncodedString(oldPassword);
        newPassword = SecurityUtil.getEncodedString(newPassword);
        if (oldPassword.equals(teacher.getPassword())) {
            teacher.setPassword(newPassword);
            teacherMapper.update(teacher);
            return true;
        }
        return false;
    }

    @Override
    public Question addQuestion(Question question) {
        questionMapper.insert(question);
        if(question.getOptionList()!=null) {
            for (Option option : question.getOptionList()) {
                option.setQuestionId(question.getId());
                optionMapper.insertSelective(option);
            }
        }
        return question;
    }

    @Override
    public PageInfo<Question> listQuestions(Integer pageNum, Integer pageSize, Teacher teacher) {
        PageHelper.startPage(pageNum, pageSize);
        List<Question> questions = questionMapper.listQuestionsByTeacherId(teacher.getId());
        return new PageInfo<>(questions);
    }

    @Override
    public PageInfo<Question> listAllQuestions(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Question> questions = questionMapper.list();
        return new PageInfo<>(questions);
    }

    @Override
    public QuestionVO getQuestion(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            return null;
        }
        QuestionVO information = new QuestionVO(question, 0, 0, 0.0);
        int answerNumber = answerMapper.getAnswerNumber(id);
        information.setAnswerNumber(answerNumber);
        int currentNumber = answerMapper.getCurrentNumber(id);
        information.setCurrentAnswerNumber(currentNumber);
        if (answerNumber != 0) {
            information.setCurrentRate((double)currentNumber / answerNumber);
        }
        return information;
    }

    @Override
    public Question updateQuestion(Question question) {
        if (question.getId() == null) {
            return null;
        }
        for (Option option : question.getOptionList()) {
            if (option.getId() == null) {
                return null;
            }
            optionMapper.updateByPrimaryKeySelective(option);
        }
        questionMapper.updateByPrimaryKeySelective(question);
        return questionMapper.selectByPrimaryKey(question.getId());
    }

    @Override
    public void deleteQuestion(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question != null) {
            for (Option option : question.getOptionList()) {
                optionMapper.deleteByPrimaryKey(option.getId());
            }
            List<Answer> answers = answerMapper.listAnswerByQuestionId(id);
            for (Answer answer : answers) {
                answerMapper.deleteByPrimaryKey(answer.getId());
            }
        }
        questionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageInfo<Answer> getAnswerList(Integer questionId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Answer> answers = answerMapper.listAnswerByQuestionId(questionId);
        return new PageInfo<>(answers);
    }
}

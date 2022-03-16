package com.kevin.onlinetest.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kevin.onlinetest.comm.CommonConstants;
import com.kevin.onlinetest.dos.AnswerDO;
import com.kevin.onlinetest.mapper.*;
import com.kevin.onlinetest.pojo.*;
import com.kevin.onlinetest.response.CommonResponse;
import com.kevin.onlinetest.service.StudentService;
import com.kevin.onlinetest.util.SecurityUtil;
import com.kevin.onlinetest.util.SessionUtil;
import com.kevin.onlinetest.vo.AnswerHistoryVO;
import com.kevin.onlinetest.vo.TestResultVO;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author herokilito
 * @version V1.0
 * @Package com.kevin.onlinetest.service.impl
 * @date 2021/1/3 18:13
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentMapper studentMapper;
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private AnswerMapper answerMapper;
    @Resource
    private OptionMapper optionMapper;
    @Resource
    private AnswerToOptionMapper answerToOptionMapper;

    @Override
    public CommonResponse<Student> login(Student student) {
        HttpSession session = SessionUtil.getSession();
        SessionUtil.removeSession();
        Student studentInfo = studentMapper.get(student.getStuNum());
        if (studentInfo != null) {
            if (SecurityUtil.check(student.getPassword(), studentInfo.getPassword())) {
                if (session != null) {
                    session.setAttribute(CommonConstants.STUDENT_SESSION_NAME, studentInfo);
                }
                return new CommonResponse<>(0, "登录成功", studentInfo);
            } else {
                return new CommonResponse<>(1, "用户名或密码错误", null);
            }
        }
        return new CommonResponse<>(1, "用户名或密码错误", null);
    }

    @Override
    public boolean updatePassword(Student student, String oldPassword, String newPassword) {
        oldPassword = SecurityUtil.getEncodedString(oldPassword);
        newPassword = SecurityUtil.getEncodedString(newPassword);
        if (oldPassword.equals(student.getPassword())) {
            student.setPassword(newPassword);
            studentMapper.update(student);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Question getQuestionById(Integer id) {
        return questionMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Question> getQuestions(Integer courseId, Integer count) {
        PageHelper.startPage(1, count);
        List<Question> questions = questionMapper.listQuestionsByCoursesId(courseId);
        questions.sort((o1, o2) -> {
            if (o1.getTypeId() > o2.getTypeId()) {
                return 1;
            } else if (o1.getTypeId().equals(o2.getTypeId())) {
                return 0;
            } else {
                return -1;
            }
        });
        return questions;
    }

    @Override
    public Answer answerObjectiveQuestion(List<Integer> optionIds) {
        return null;
    }

    @Override
    public TestResultVO completeTest(Integer studentId, List<Question> questions, List<AnswerDO> answers) {
        TestResultVO testResult = new TestResultVO();
        testResult.setQuestionCount(questions.size());
        testResult.setQuestionList(questions);
        Map<Integer, AnswerDO> answerMap = new HashMap<>(16);
        for (AnswerDO answer : answers) {
            answerMap.put(answer.getQuestionId(), answer);
        }
        List<Boolean> resultList = new ArrayList<>();
        List<String> answerContentList = new ArrayList<>();
        List<List<Integer>> answerOptionIdList = new ArrayList<>();
        int totalPoint = 0;
        int correctCount = 0;
        for (Question question : questions) {
            AnswerDO answer = answerMap.get(question.getId());
            if (answer != null) {
                Answer answerData = new Answer();
                answerData.setStudentId(studentId);
                answerData.setContent(answer.getAnswerContent());
                answerData.setQuestionId(question.getId());
                answerContentList.add(answer.getAnswerContent());
                List<Integer> optionIds = answer.getOptionList();
                List<Integer> answerOptionIds = new ArrayList<>();
                if (optionIds == null || optionIds.size() == 0) {
                    // 其他题型
                    if (question.getType().getId().equals(3)) {
                        // 填空题
                        if (answer.getAnswerContent().replaceAll(" ", "").equals(question.getRefAnswer())) {
                            resultList.add(true);
                            answerData.setCorrect(true);
                            answerData.setScore(10);
                            totalPoint += 10;
                            correctCount++;
                        } else {
                            resultList.add(false);
                            answerData.setCorrect(false);
                            answerData.setScore(0);
                        }
                    } else {
                        // 未答题
                        answerData.setScore(null);
                        answerData.setCorrect(false);
                    }
                    answerMapper.insertSelective(answerData);
                } else if (question.getType().getId().equals(1)) {
                    // 单选题
                    Option option = optionMapper.selectByPrimaryKey(optionIds.get(0));
                    answerOptionIds.add(option.getId());
                    if (option.getTruth()) {
                        resultList.add(true);
                        answerData.setCorrect(true);
                        answerData.setScore(10);
                        totalPoint += 10;
                        correctCount++;
                    } else {
                        resultList.add(false);
                        answerData.setCorrect(false);
                    }
                    answerMapper.insertSelective(answerData);
                    answerToOptionMapper.insert(new AnswerToOption(null, answerData.getId(), option.getId()));
                } else if (question.getType().getId().equals(2)){
                    // 多选题
                    int correctOptionCount = (int) question.getOptionList().stream().filter(Option::getTruth).count();
                    int correctAnswerCount = 0;
                    boolean flag = true;
                    for (Integer optionId : optionIds) {
                        answerOptionIds.add(optionId);
                        Option option = optionMapper.selectByPrimaryKey(optionId);
                        if (option.getTruth()) {
                            correctAnswerCount ++;
                        } else {
                            flag = false;
                            break;
                        }
                    }
                    if (!flag) {
                        answerData.setCorrect(false);
                        answerData.setScore(0);
                        resultList.add(false);
                    } else if (correctAnswerCount < correctOptionCount) {
                        answerData.setCorrect(false);
                        answerData.setScore(5);
                        resultList.add(false);
                        totalPoint += 5;
                    } else {
                        answerData.setScore(10);
                        answerData.setCorrect(true);
                        resultList.add(true);
                        totalPoint += 10;
                        correctCount++;
                    }
                    answerMapper.insertSelective(answerData);
                    optionIds.forEach(id -> {
                        answerToOptionMapper.insert(new AnswerToOption(null, answerData.getId(), id));
                    });
                }
                answerOptionIdList.add(answerOptionIds);
            } else {
                resultList.add(null);
                answerContentList.add(null);
                answerOptionIdList.add(null);
            }
        }
        testResult.setTotalScore(totalPoint);
        testResult.setCorrectCount(correctCount);
        testResult.setResultList(resultList);
        testResult.setAnswerContentList(answerContentList);
        testResult.setAnswerOptionIdList(answerOptionIdList);
        return testResult;
    }

    @Override
    public PageInfo<AnswerHistoryVO> listAnswerHistory(Integer studentId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Answer> answers = answerMapper.listAnswerByStudentId(studentId);
        List<AnswerHistoryVO> answerHistories = new ArrayList<>();
        answers.forEach(answer -> {
            answer.setAnswerOptions(answerToOptionMapper.listOptionByAnswerId(answer.getId()));
            Question question = questionMapper.selectByPrimaryKey(answer.getQuestionId());
            answerHistories.add(new AnswerHistoryVO(question, answer));
        });
        PageInfo<Answer> pAnswers = new PageInfo<>(answers);
        PageInfo<AnswerHistoryVO> pAnswerHistory = new PageInfo<>();
        pAnswerHistory.setPageNum(pAnswers.getPageNum());
        pAnswerHistory.setPageSize(pAnswers.getPageSize());
        pAnswerHistory.setTotal(pAnswers.getTotal());
        pAnswerHistory.setIsFirstPage(pAnswers.isIsFirstPage());
        pAnswerHistory.setIsLastPage(pAnswers.isIsLastPage());
        pAnswerHistory.setPages(pAnswers.getPages());
        pAnswerHistory.setList(answerHistories);
        return pAnswerHistory;
    }
}

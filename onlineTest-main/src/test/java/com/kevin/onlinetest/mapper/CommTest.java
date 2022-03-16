package com.kevin.onlinetest.mapper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kevin.onlinetest.pojo.Admin;
import com.kevin.onlinetest.pojo.Question;
import com.kevin.onlinetest.pojo.Student;
import com.kevin.onlinetest.service.StudentService;
import com.kevin.onlinetest.util.SecurityUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class CommTest {

    @Resource
    private StudentMapper studentMapper;
    @Resource
    private AdminMapper adminMapper;
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private StudentService studentService;
    @Resource
    private AnswerToOptionMapper answerToOptionMapper;

    @Test
    void testStudentMapper() {
        Student student = new Student();
        student.setStuNum(221701328);
        student.setName("张春翔");
        student.setPassword(SecurityUtil.getEncodedString("123456"));
        student.setId(1);
        studentMapper.update(student);
    }

    @Test
    void testAdminMapper() {
        Admin admin = new Admin();
        admin.setId(1);
        admin.setAccount("admin");
        admin.setPassword(SecurityUtil.getEncodedString("1234"));
        adminMapper.update(admin);
        System.out.println(adminMapper.get(1));
    }

    @Test
    void testQA() {
        //PageHelper.startPage(1, 10);
        List<Question> list = questionMapper.list();
        System.out.println(list);
    }

    @Test
    void testStudent() {
        List<Question> questions = studentService.getQuestions(1, 10);
        List<Question> questions2 = studentService.getQuestions(1, 10);
        System.out.println("ok");
    }

    @Test
    void testAtoO() {
        System.out.println(answerToOptionMapper.listOptionByAnswerId(54));
    }
}

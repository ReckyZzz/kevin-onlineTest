package com.kevin.onlinetest.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kevin.onlinetest.comm.CommonConstants;
import com.kevin.onlinetest.mapper.*;
import com.kevin.onlinetest.pojo.*;
import com.kevin.onlinetest.service.AdminService;
import com.kevin.onlinetest.util.SecurityUtil;
import com.kevin.onlinetest.util.SessionUtil;
import com.kevin.onlinetest.response.CommonResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author herokilito
 */
@Service
public class AdminServiceImpl implements AdminService {

    private final String originPassword = "123456";

    @Resource
    private AdminMapper adminMapper;
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private TeacherMapper teacherMapper;
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private AnswerMapper answerMapper;
    @Resource
    private OptionMapper optionMapper;
    @Resource
    private CourseMapper courseMapper;

    @Override
    public CommonResponse<Admin> login(Admin admin) {
        HttpSession session = SessionUtil.getSession();
        SessionUtil.removeSession();
        Admin adminInfo = adminMapper.getAdminByAccount(admin.getAccount());
        if (adminInfo != null) {
            if (SecurityUtil.check(admin.getPassword(), adminInfo.getPassword())) {
                if (session != null) {
                    session.setAttribute(CommonConstants.ADMIN_SESSION_NAME, adminInfo);
                }
                return new CommonResponse<>(1, "登录成功" ,adminInfo);
            } else {
                return new CommonResponse<>(2, "用户名或密码错误", null);
            }
        } else {
            return new CommonResponse<>(2, "用户名或密码错误", null);
        }
    }

    @Override
    public CommonResponse<PageInfo<Student>> getStudentInfo(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Student> students = studentMapper.list();
        PageInfo<Student> pageInfo = new PageInfo<>(students);
        return new CommonResponse<>(0, "查询成功", pageInfo);
    }

    @Override
    public CommonResponse<PageInfo<Teacher>> getTeacherInfo(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Teacher> students = teacherMapper.list();
        PageInfo<Teacher> pageInfo = new PageInfo<>(students);
        return new CommonResponse<>(0, "查询成功", pageInfo);
    }

    @Override
    public void deleteTeachers(List<Integer> teacherIds) {
        for (int id : teacherIds) {
            teacherMapper.delete(id);
        }
    }

    @Override
    public void deleteStudents(List<Integer> studentIds) {
        for (int id : studentIds) {
            studentMapper.delete(id);
        }
    }

    @Override
    public Student getStudentByNum(Integer id) {
        return studentMapper.get(id);
    }

    @Override
    public Teacher getTeacherByNum(Integer id) {
        return teacherMapper.get(id);
    }

    @Override
    public Student updateStudent(Student student) {
        if (student.getId() != null) {
            Student oldStudent = studentMapper.getById(student.getId());
            if (oldStudent == null) {
                return null;
            }
            if (student.getName() != null) {
                oldStudent.setName(student.getName());
            }
            if (student.getStuNum() != null) {
                oldStudent.setStuNum(student.getStuNum());
            }
            studentMapper.update(oldStudent);
            return oldStudent;
        }
        return null;
    }

    @Override
    public Teacher updateTeacher(Teacher teacher) {
        if (teacher.getId() != null) {
            Teacher oldTeacher = teacherMapper.getById(teacher.getId());
            if (oldTeacher == null) {
                return null;
            }
            if (teacher.getName() != null) {
                oldTeacher.setName(teacher.getName());
            }
            if (teacher.getTeacherNum() != null) {
                oldTeacher.setTeacherNum(teacher.getTeacherNum());
            }
            teacherMapper.update(oldTeacher);
            return oldTeacher;
        }
        return null;
    }

    @Override
    public Question getQuestionById(Integer id) {
        return questionMapper.selectByPrimaryKey(id);
    }

    @Override
    public Answer getAnswerById(Integer id) {
        return answerMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<Question> listQuestions(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Question> questions = questionMapper.list();
        return new PageInfo<>(questions);
    }

    @Override
    public PageInfo<Answer> listAnswers(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Answer> answers = answerMapper.list();
        return new PageInfo<>(answers);
    }

    @Override
    public void restStudentPwd(Integer id) {
        String pwd = SecurityUtil.getEncodedString(originPassword);
        studentMapper.resetPassword(id, pwd);
    }

    @Override
    public void restTeacherPwd(Integer id) {
        String pwd = SecurityUtil.getEncodedString(originPassword);
        teacherMapper.resetPassword(id, pwd);
    }

    @Override
    public Teacher addTeacher(Teacher teacher) {
        teacher.setPassword(SecurityUtil.getEncodedString(originPassword));
        if (teacherMapper.get(teacher.getTeacherNum()) != null) {
            return null;
        }
        teacherMapper.insert(teacher);
        return teacher;
    }

    @Override
    public Student addStudent(Student student) {
        student.setPassword(SecurityUtil.getEncodedString(originPassword));
        if (studentMapper.get(student.getStuNum()) != null) {
            return null;
        }
        studentMapper.insert(student);
        return student;
    }

    @Override
    public void deleteQuestions(List<Integer> questionIds) {
        for(int id : questionIds) {
            Question question = questionMapper.selectByPrimaryKey(id);
            if (!question.getOptionList().isEmpty()) {
                for (Option option : question.getOptionList()) {
                    optionMapper.deleteByPrimaryKey(option.getId());
                }
            }
            questionMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public PageInfo<Course> listCourse(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(courseMapper.list());
    }

    @Override
    public Course getCourse(Integer courseId) {
        return courseMapper.selectByPrimaryKey(courseId);
    }

    @Override
    public void deleteCourse(List<Integer> courseIds) {
        courseIds.forEach(id -> {
            courseMapper.deleteByPrimaryKey(id);
        });
    }

    @Override
    public Course addCourse(Course course) {
        courseMapper.insertSelective(course);
        return course;
    }

    @Override
    public Course updateCourse(Course course) {
        courseMapper.updateByPrimaryKeySelective(course);
        return course;
    }

    @Override
    public Admin get(Integer id) {
        return adminMapper.get(id);
    }

    @Override
    public List<Admin> list() {
        return adminMapper.list();
    }

    @Override
    public void insert(Admin object) {
        adminMapper.insert(object);
    }

    @Override
    public void update(Admin object) {
        adminMapper.update(object);
    }

    @Override
    public void delete(Integer id) {
        adminMapper.delete(id);
    }
}

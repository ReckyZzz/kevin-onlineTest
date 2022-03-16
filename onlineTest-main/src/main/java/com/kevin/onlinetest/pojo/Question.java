package com.kevin.onlinetest.pojo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * question
 * @author 
 */
@Data
public class Question implements Serializable {
    private Integer id;

    private String title;

    private String content;

    private Integer teacherId;

    private Integer typeId;

    private String refAnswer;

    private Integer courseId;

    private List<Option> optionList;

    private Type type;

    private Course course;

    private Teacher teacher;

    private static final long serialVersionUID = 1L;
}
package com.kevin.onlinetest.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * course
 * @author 
 */
@Data
public class Course implements Serializable {
    private Integer id;

    private String name;

    private String description;

    private static final long serialVersionUID = 1L;
}
package com.kevin.onlinetest.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * option
 * @author 
 */
@Data
public class Option implements Serializable {
    private Integer id;

    private String content;

    private Boolean truth;

    private Integer questionId;

    private static final long serialVersionUID = 1L;
}
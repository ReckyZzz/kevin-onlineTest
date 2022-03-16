package com.kevin.onlinetest.pojo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * answer_to_option
 * @author 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerToOption implements Serializable {
    private Integer id;

    private Integer answerId;

    private Integer optionId;

    private static final long serialVersionUID = 1L;
}
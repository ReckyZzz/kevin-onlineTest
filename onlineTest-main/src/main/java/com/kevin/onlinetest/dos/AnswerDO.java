package com.kevin.onlinetest.dos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author herokilito
 * @version V1.0
 * @Package com.kevin.onlinetest.dos
 * @date 2021/1/4 18:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDO {

    private Integer questionId;

    private String answerContent;

    private List<Integer> optionList;
}

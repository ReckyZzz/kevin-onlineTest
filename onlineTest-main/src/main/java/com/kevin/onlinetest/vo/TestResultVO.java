package com.kevin.onlinetest.vo;

import com.kevin.onlinetest.pojo.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author herokilito
 * @version V1.0
 * @Package com.kevin.onlinetest.vo
 * @date 2021/1/4 18:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestResultVO {

    private Integer questionCount;

    private Integer correctCount;

    private Integer totalScore;

    private List<Question> questionList;

    private List<Boolean> resultList;

    private List<String> answerContentList;

    private List<List<Integer>> answerOptionIdList;


}

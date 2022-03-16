package com.kevin.onlinetest.vo;

import com.kevin.onlinetest.pojo.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author herokilito
 * @version V1.0
 * @Package com.kevin.onlinetest.vo
 * @date 2021/1/3 17:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionVO {

    private Question question;

    private Integer answerNumber;

    private Integer currentAnswerNumber;

    private Double currentRate;
}

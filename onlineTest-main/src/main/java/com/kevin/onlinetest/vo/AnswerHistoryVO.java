package com.kevin.onlinetest.vo;

import com.kevin.onlinetest.pojo.Answer;
import com.kevin.onlinetest.pojo.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author herokilito
 * @version V1.0
 * @Package com.kevin.onlinetest.vo
 * @date 2021/1/8 19:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerHistoryVO {

    private Question question;

    private Answer answer;
}

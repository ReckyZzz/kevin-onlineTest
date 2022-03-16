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
 * @date 2021/1/4 17:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestVO {

    private List<Question> questionList;

    private Integer questionCount;
}

package com.kevin.onlinetest.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author herokilito
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse<T> {

    private Integer code;
    private String message;
    private T data;
}

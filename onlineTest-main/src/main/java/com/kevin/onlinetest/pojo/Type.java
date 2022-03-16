package com.kevin.onlinetest.pojo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * type 实体类
 * @author herokilito
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Type implements Serializable {
    private Integer id;

    private String name;

    private static final long serialVersionUID = 1L;
}
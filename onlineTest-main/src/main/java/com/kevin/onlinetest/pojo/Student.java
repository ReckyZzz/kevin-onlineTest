package com.kevin.onlinetest.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author herokilito
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {

  private Integer id;

  private Integer stuNum;

  private String name;

  private String password;

  private static final long serialVersionUID = 1L;

}

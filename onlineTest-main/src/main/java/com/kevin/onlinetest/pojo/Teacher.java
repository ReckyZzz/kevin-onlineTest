package com.kevin.onlinetest.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author herokilito
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher implements Serializable {

  private Integer id;

  private String name;

  private Integer teacherNum;

  private String password;

  private static final long serialVersionUID = 1L;

}

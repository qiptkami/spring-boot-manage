package com.yiqiandewo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Employee implements Serializable {

    private Integer id;
    private String name;

    private String email;
    //1 male, 0 female
    private Integer gender;
    private Integer departmentId;
    private Date birth;

    //一对一
    private Department department;
}

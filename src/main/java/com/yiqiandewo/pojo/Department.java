package com.yiqiandewo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Department implements Serializable {
    private Integer id;
    private String departmentName;

/*    //一对多
    private List<Employee> employees;*/
}

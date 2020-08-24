package com.yiqiandewo.service;

import com.yiqiandewo.pojo.Department;

import java.util.List;

public interface IDepartmentService {

    public List<Department> queryAll();

    public Department queryById(Integer id);

    public void save(Department department);

    public void del(Integer id);

    public void update(Department department);

/*    //一对多  一门部门有多个员工
    public List<Department> queryAllEmployee();*/
}

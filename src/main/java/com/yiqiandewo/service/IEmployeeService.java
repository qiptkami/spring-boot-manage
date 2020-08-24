package com.yiqiandewo.service;

import com.yiqiandewo.pojo.Employee;

import java.util.List;

public interface IEmployeeService {
    public List<Employee> queryAll();

    public Employee queryById(Integer id);

    public void save(Employee employee);

    public void del(Integer id);

    public void update(Employee employee);

    //一对一 一个员工对应一个部门
    public List<Employee> queryAllDepartment();
}

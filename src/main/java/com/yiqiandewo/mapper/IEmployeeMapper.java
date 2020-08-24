package com.yiqiandewo.mapper;

import com.yiqiandewo.pojo.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface IEmployeeMapper {

    public List<Employee> queryAll();

    public Employee queryById(Integer id);

    public void save(Employee employee);

    public void del(Integer id);

    public void update(Employee employee);

    //一对一 一个员工对应一个部门
    public List<Employee> queryAllDepartment();

}

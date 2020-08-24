package com.yiqiandewo.mapper;

import com.yiqiandewo.pojo.Department;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface IDepartmentMapper {

    public List<Department> queryAll();

    public Department queryById(Integer id);

    public void save(Department department);

    public void del(Integer id);

    public void update(Department department);

/*    //一对多  一门部门有多个员工
    public List<Department> queryAllEmployee();*/

}

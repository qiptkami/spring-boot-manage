package com.yiqiandewo.controller;

import com.yiqiandewo.pojo.Department;
import com.yiqiandewo.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DepartmentController {

    @Autowired
    IDepartmentService departmentService;

    @Secured("ROLE_root")
    @GetMapping("/dep/all")
    public List<Department> queryAll() {
        return departmentService.queryAll();
    }

    @Secured("ROLE_root")
    @GetMapping("/dep/{id}")
    public Department queryById(@PathVariable("id") Integer id) {
        return departmentService.queryById(id);
    }

    @Secured("ROLE_root")
    @GetMapping("/dep/save")
    public void save(Department department) {
        departmentService.save(department);
    }

    @Secured("ROLE_root")
    @GetMapping("/dep/del/{id}")
    public void del(@PathVariable("id") Integer id) {
        departmentService.del(id);
    }

    @Secured("ROLE_root")
    @GetMapping("/dep/update")
    public void update(Department department) {
        departmentService.update(department);
    }

    /*//一对多  一门部门有多个员工
    @GetMapping("/dep/allEmp")
    public List<Department> queryAllEmployee() {
        return departmentService.queryAllEmployee();
    }*/
}

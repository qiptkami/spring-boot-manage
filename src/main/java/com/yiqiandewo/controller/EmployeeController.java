package com.yiqiandewo.controller;

import com.yiqiandewo.pojo.Department;
import com.yiqiandewo.pojo.Employee;
import com.yiqiandewo.service.IDepartmentService;
import com.yiqiandewo.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    IEmployeeService employeeService;

    @Autowired
    IDepartmentService departmentService;

    @Secured("ROLE_root")  //需要将@EnableGlobalMethodSecurity的属性securedEnabled配置为true //权限名区分大小写
    @GetMapping("/list")
    public String queryAll(Model model) {
        List<Employee> list = employeeService.queryAllDepartment();
        model.addAttribute("emps", list);
        return "emp/list";
    }

    @Secured("ROLE_root")
    @GetMapping("/emp/{id}")
    public String toUpdatePage(@PathVariable("id") Integer id, Model model) {
        Employee employee = employeeService.queryById(id);
        Department department = departmentService.queryById(employee.getDepartmentId());
        employee.setDepartment(department);
        model.addAttribute("emp", employee);

        List<Department> departments = departmentService.queryAll();

        model.addAttribute("depts", departments);
        return "emp/update";
    }

    @Secured("ROLE_root")
    @PutMapping("/emp")
    public String update(Employee employee, Department department) {
        System.out.println(department);
        System.out.println(employee);
        employeeService.update(employee);
        return "redirect:/list";
    }

    @Secured("ROLE_root")
    @DeleteMapping("/emp/{id}")
    public String del(@PathVariable("id") Integer id) {
        employeeService.del(id);
        return "redirect:/list";
    }

    @Secured("ROLE_root")
    @GetMapping("/emp")
    public String toAddPage(Model model) {
        List<Department> departments = departmentService.queryAll();
        model.addAttribute("depts", departments);
        return "emp/add";
    }

    @Secured("ROLE_root")
    @PostMapping("/emp")
    public String save(Employee employee) {
        employeeService.save(employee);
        return "redirect:/list";
    }

}

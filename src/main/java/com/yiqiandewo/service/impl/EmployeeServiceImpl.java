package com.yiqiandewo.service.impl;

import com.yiqiandewo.mapper.IEmployeeMapper;
import com.yiqiandewo.pojo.Department;
import com.yiqiandewo.pojo.Employee;
import com.yiqiandewo.service.IDepartmentService;
import com.yiqiandewo.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    IDepartmentService departmentService;

    @Autowired
    IEmployeeMapper employeeMapper;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<Employee> queryAll() {
        return employeeMapper.queryAll();
    }

    @Override
    public Employee queryById(Integer id) {
        //首先查询缓存
        Boolean hasKey = redisTemplate.hasKey("emp"+id);
        Employee employee;

        if (hasKey) {
            employee = (Employee) redisTemplate.opsForValue().get("emp"+id);
        } else {  //如果缓存中没有
            //查询数据库
            employee = employeeMapper.queryById(id);

            //放入缓存
            redisTemplate.opsForValue().set("emp" + id, employee);
        }

        return employee;
    }

    @Override
    public void save(Employee employee) {
        //存入数据库
        employeeMapper.save(employee);
        //需要将department信息封装
        Department department = (Department) redisTemplate.opsForValue().get("dep" + employee.getDepartmentId());
        employee.setDepartment(department);
        //放入缓存
        redisTemplate.opsForValue().set("emp" + employee.getId(), employee);
    }

    @Override
    public void del(Integer id) {
        //从缓存中删除
        redisTemplate.delete("emp"+id);
        employeeMapper.del(id);
    }

    @Override
    public void update(Employee employee) {
        redisTemplate.delete("emp"+employee.getId());
        
        employeeMapper.update(employee);
        //需要将department信息封装
        //查询缓存
        Department department = (Department) redisTemplate.opsForValue().get("dep" + employee.getDepartmentId());
        employee.setDepartment(department);
        redisTemplate.opsForValue().set("emp" + employee.getId(), employee);
    }

    @Override
    public List<Employee> queryAllDepartment() {
        //查询Dept的缓存
        departmentService.queryAll();
        //首先查询缓存
        Set<String> keys = redisTemplate.keys("emp*");
        List<Employee> list;

        if (keys != null && keys.size() != 0) {
            list = new ArrayList<>();

            for (String key : keys) {
                list.add((Employee) redisTemplate.opsForValue().get(key));
            }
        } else {
            //如果缓存中没有
            //查询数据库
            list = employeeMapper.queryAllDepartment();
            //放入缓存
            for (Employee employee : list) {
                redisTemplate.opsForValue().set("emp" + employee.getId(), employee);
            }
        }
        return list;
    }
}
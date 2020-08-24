package com.yiqiandewo.service.impl;

import com.yiqiandewo.mapper.IDepartmentMapper;
import com.yiqiandewo.pojo.Department;
import com.yiqiandewo.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class DepartmentServiceImpl implements IDepartmentService {

    @Autowired
    IDepartmentMapper departmentMapper;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<Department> queryAll() {
        //首先查询缓存
        Set<String> keys = redisTemplate.keys("dep*");
        List<Department> list;

        if (keys != null && keys.size() != 0) {

            list = new ArrayList<>();
            for (String key : keys) {
                list.add((Department) redisTemplate.opsForValue().get(key));
            }

        } else {
            //如果缓存中没有
            //查询数据库
            list = departmentMapper.queryAll();
            //放入缓存
            for (Department department : list) {
                redisTemplate.opsForValue().set("dep" + department.getId(), department);
            }
        }
        return list;
    }

    @Override
    public Department queryById(Integer id) {
        return departmentMapper.queryById(id);
    }

    @Override
    public void save(Department department) {
        departmentMapper.save(department);
    }

    @Override
    public void del(Integer id) {
        departmentMapper.del(id);
    }

    @Override
    public void update(Department department) {
        departmentMapper.update(department);
    }

/*    @Override
    public List<Department> queryAllEmployee() {
        return departmentMapper.queryAllEmployee();
    }*/
}

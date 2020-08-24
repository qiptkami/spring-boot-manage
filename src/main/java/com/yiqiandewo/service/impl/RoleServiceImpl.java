package com.yiqiandewo.service.impl;

import com.yiqiandewo.mapper.IRoleMapper;
import com.yiqiandewo.mapper.IUserMapper;
import com.yiqiandewo.pojo.Role;
import com.yiqiandewo.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    IRoleMapper roleMapper;

    @Autowired
    IUserMapper userMapper;

   @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.yiqiandewo.pojo.User user = userMapper.queryByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        //添加权限
        Integer id = user.getRoleId();
        Role role = roleMapper.queryById(id);

        return new User(user.getUsername(), bCryptPasswordEncoder.encode(user.getPassword()), getAuthority(role));
    }

    public List<SimpleGrantedAuthority> getAuthority(Role role) {
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        return list;
    }

    @Override
    public Role queryById(Integer id) {
        return roleMapper.queryById(id);
    }

}

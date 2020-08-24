package com.yiqiandewo.service;

import com.yiqiandewo.pojo.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IRoleService extends UserDetailsService {
    public Role queryById(Integer id);
}

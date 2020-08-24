package com.yiqiandewo.mapper;

import com.yiqiandewo.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface IUserMapper {

    @Select("select * from user where username = #{username}")
    public User queryByUsername(String username);
}

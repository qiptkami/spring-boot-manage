package com.yiqiandewo.mapper;

import com.yiqiandewo.pojo.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface IRoleMapper {

    @Select("select * from role where id = #{id}")
    public Role queryById(Integer id);
}

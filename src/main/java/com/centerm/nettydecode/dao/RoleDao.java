package com.centerm.nettydecode.dao;

import com.centerm.nettydecode.pojo.user.Role;
import com.centerm.nettydecode.pojo.user.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface RoleDao {
    List<Role>findRoleByUser(User user);
}

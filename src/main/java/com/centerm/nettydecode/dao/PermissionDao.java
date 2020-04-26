package com.centerm.nettydecode.dao;

import com.centerm.nettydecode.pojo.user.Permission;
import com.centerm.nettydecode.pojo.user.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PermissionDao {

    List<Permission> findPermissionByRole(String name);
}

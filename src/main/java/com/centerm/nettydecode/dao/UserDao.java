package com.centerm.nettydecode.dao;

import com.centerm.nettydecode.pojo.user.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Sheva
 * @date 2020/4/21 14:54
 * @description
 */
@Mapper
public interface UserDao {

    /**
     * 根据用户名寻找用户
     * @param username
     * @return
     */
    User findByUsername(String username);
}

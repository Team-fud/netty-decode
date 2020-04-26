package com.centerm.nettydecode.pojo.user;

import lombok.Data;
/**
 * @author Sheva
 * @date 2020/4/21 14:22
 * @description
 */
@Data
public class User {
    /**
     * 用户id
     */
    private int id;
    /**
     * 用户名称
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
}

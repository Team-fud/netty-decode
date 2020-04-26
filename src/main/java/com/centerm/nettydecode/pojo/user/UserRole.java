package com.centerm.nettydecode.pojo.user;

import lombok.Data;

@Data
public class UserRole {
    /**
     * id
     */
    private Integer id;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 角色id
     */
    private Integer roleId;
}

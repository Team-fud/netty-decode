package com.centerm.nettydecode.pojo.user;

import lombok.Data;

@Data
public class RolePermission {
    /**
     * id
     */
    private Integer id;
    /**
     *  角色id
     */
    private Integer roleId;
    /**
     * 权限id
     */
    private Integer permissionId;
}

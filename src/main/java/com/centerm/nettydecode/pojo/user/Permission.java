package com.centerm.nettydecode.pojo.user;

import lombok.Data;

@Data
public class Permission {
    /**
     * 权限名称
     */
    private Integer id;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 权限校验字符串
     */
    private String perCode;
}

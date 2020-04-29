package com.centerm.nettydecode.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class SysLog {
    private Long id;
    private String createTime;
    private String description;
    private String username;
    private String params;
    private String reqIp;
    private String logType;
    private String exceptionDetail;
    private String method;
    private Long executeTime;
}

package com.centerm.nettydecode.pojo;

import lombok.Data;

@Data
public class ReqRecord {
    private Long id;
    private String sn;
    private String ip;
    private String reqData;
    private String rspData;
    private Long executeTime;
}

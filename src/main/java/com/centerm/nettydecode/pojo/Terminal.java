package com.centerm.nettydecode.pojo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Terminal {
    private Long id;
    private String cateName;
    private Timestamp date;
}

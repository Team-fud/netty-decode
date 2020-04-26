package com.centerm.nettydecode.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class Terminal {
    private Long id;
    private String cateName;
    private Timestamp date;

    public Terminal(String cateName) {
        this.cateName = cateName;
    }

    public Terminal() {
    }
}

package com.centerm.nettydecode.service;

import com.centerm.nettydecode.pojo.SysLog;

import java.util.List;

public interface SysService {
    List<SysLog> getAllLog();
    boolean deleteLog(int id);
    List<SysLog> search(String content);
    SysLog findById(int id);
    boolean updateLog(SysLog log);
}

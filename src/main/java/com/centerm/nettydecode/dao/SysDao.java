package com.centerm.nettydecode.dao;

import com.centerm.nettydecode.pojo.SysLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface SysDao {
    List<SysLog> getAllLog();
    boolean deleteLog(int id);
    List<SysLog> search(String content);
    SysLog findById(int id);
    boolean updateLog(SysLog log);
}

package com.centerm.nettydecode.dao;

import com.centerm.nettydecode.pojo.Terminal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TerminalDao {
    List<Terminal> getAllTerminals();
    List<Terminal> search(String keywords);
    int deleteTerminals(@Param("ids") String[] ids);
    int updateTerminal(Terminal terminal);
    int addNewTerminal(Terminal terminal);
    Terminal selectByPrimaryKey(int id);
}

package com.centerm.nettydecode.service;

import com.centerm.nettydecode.pojo.Terminal;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TerminalService {
    List<Terminal> getAllTerminals();
    List<Terminal> search(String keywords);
    Boolean deleteTerminal(String ids);
    int updateTerminal(Terminal terminal);
    int addNewTerminal(Terminal terminal);
    Terminal selectByPrimaryKey(int id);
}

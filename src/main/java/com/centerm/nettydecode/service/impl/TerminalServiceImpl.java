package com.centerm.nettydecode.service.impl;

import com.centerm.nettydecode.dao.TerminalDao;
import com.centerm.nettydecode.pojo.Terminal;
import com.centerm.nettydecode.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TerminalServiceImpl implements TerminalService {
    @Autowired
   private TerminalDao terminalDao;
    @Override
    public List<Terminal> getAllTerminals() {
        return terminalDao.getAllTerminals();
    }

    @Override
    public List<Terminal> search(String keywords) {
        return terminalDao.search(keywords);
    }

    @Override
    public Boolean deleteTerminal(String ids) {
        String[] spilt = ids.split(",");
        int result = terminalDao.deleteTerminals(spilt);
        return result == spilt.length;
    }

    @Override
    public int updateTerminal(Terminal terminal) {
        return terminalDao.updateTerminal(terminal);
    }

    @Override
    public int addNewTerminal(Terminal terminal) {
        return terminalDao.addNewTerminal(terminal);
    }

    @Override
    public Terminal selectByPrimaryKey(int id) {
        return terminalDao.selectByPrimaryKey(id);
    }
}

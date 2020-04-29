package com.centerm.nettydecode.service.impl;

import com.centerm.nettydecode.dao.SysDao;
import com.centerm.nettydecode.pojo.SysLog;
import com.centerm.nettydecode.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SysServiceImpl implements SysService {
    @Autowired
    public SysDao logDao;
    @Override
    public List<SysLog> getAllLog() {
        return logDao.getAllLog();
    }

    @Override
    public boolean deleteLog(int id) {
        return logDao.deleteLog(id);
    }

    @Override
    public List<SysLog> search(String content) {
        return logDao.search(content);
    }

    @Override
    public SysLog findById(int id) {
        return logDao.findById(id);
    }

    @Override
    public boolean updateLog(SysLog log) {
        return logDao.updateLog(log);
    }
}

package com.centerm.nettydecode.service.impl;

import com.centerm.nettydecode.dao.ReqDao;
import com.centerm.nettydecode.pojo.ReqRecord;
import com.centerm.nettydecode.service.ReqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReqServiceImpl implements ReqService {
    @Autowired
    public ReqDao reqDao;
    @Override
    public List<ReqRecord> getAllReq() {
        return reqDao.getAllReq();
    }

    @Override
    public boolean deleteReq(int id) {
        return reqDao.deleteReq(id);
    }

    @Override
    public ReqRecord findById(int id) {
        return reqDao.findById(id);
    }

    @Override
    public List<ReqRecord> search(String content) {
        return reqDao.search(content);
    }
}

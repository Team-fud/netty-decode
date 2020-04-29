package com.centerm.nettydecode.service;

import com.centerm.nettydecode.pojo.ReqRecord;

import java.util.List;

public interface ReqService {
    List<ReqRecord> getAllReq();
    boolean deleteReq(int id);
    ReqRecord findById(int id);
    List<ReqRecord> search(String content);
}

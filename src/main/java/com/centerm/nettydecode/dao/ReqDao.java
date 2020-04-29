package com.centerm.nettydecode.dao;

import com.centerm.nettydecode.pojo.ReqRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReqDao {
    List<ReqRecord> getAllReq();
    boolean deleteReq(int id);
    ReqRecord findById(int id);
    List<ReqRecord> search(String content);
}

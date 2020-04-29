package com.centerm.nettydecode.controller;

import com.centerm.nettydecode.pojo.ReqRecord;
import com.centerm.nettydecode.pojo.Result;
import com.centerm.nettydecode.service.ReqService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/vue/req")
public class ReqController {
    @Autowired
    public ReqService reqService;
    @GetMapping("/getAllReq/{page}")
    public Result getAllReq(@PathVariable(required = false) String page){
        System.out.println("开始查询所有req信息");
        System.out.println("当前页面为：" + page);
        if (page == null || page == "" || Integer.parseInt(page)<1){
            page="1";
        }
        PageHelper.startPage(Integer.parseInt(page),5);
        List<ReqRecord> reqRecordList = reqService.getAllReq();
        PageInfo<ReqRecord> selectPage = new PageInfo<>(reqRecordList);

        if (reqRecordList == null){
            return new Result("warn","req查询失败",null);
        }
        Map<String, Object> result = new HashMap<String, Object>(16);
        result.put("data",selectPage.getList());
        result.put("total",selectPage.getTotal());
        return new Result("success","查询所有req成功",result);
    }
    @DeleteMapping(value = "/delete/{id}")
    @CrossOrigin
    public Result deleteLog(@PathVariable String id){
        System.out.println("开始删除 "+id);
        boolean res = reqService.deleteReq(Integer.parseInt(id));
        if (res){
            return new Result("success","删除失败",null);
        }
        return new Result("warn","删除失败",null);
    }
    @GetMapping("/{id}")
    @RequiresRoles(logical = Logical.AND, value = {"admin"})
    @CrossOrigin
    public Result findById(@PathVariable(value = "id") Integer id) {
        System.out.println("开始查询id= "+id);
        ReqRecord reqRecord= reqService.findById(id);
        if (reqRecord == null) {
            System.out.println("查询id失败");
            return new Result("warn", "查询失败(Query was fail)", null);
        }
        System.out.println("查询id成功");
        return new Result("success", "查询成功(Query was successful)", reqRecord);
    }
    @GetMapping("/search/{content}")
    @CrossOrigin
    @RequiresRoles(logical = Logical.AND, value = {"admin"})
    public Result search(@PathVariable String content){
        System.out.println("开始搜索Req信息");
        List<ReqRecord> reqRecordList= reqService.search(content);
        for(ReqRecord r : reqRecordList){
            System.out.println("查询到的req：");
            System.out.println(r.getSn());
        }
        if (reqRecordList.isEmpty()){
            return new Result("warn","未搜索到req信息",null);
        }
        return new Result("success","成功获取到搜索的req信息",reqRecordList);
    }

}

package com.centerm.nettydecode.controller;

import com.centerm.nettydecode.pojo.Result;
import com.centerm.nettydecode.pojo.SysLog;
import com.centerm.nettydecode.service.SysService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/vue/sys")
public class SysController {
    @Autowired
    public SysService sysService;

    @GetMapping("/getAllLog/{page}")
    @CrossOrigin
   @RequiresAuthentication
    public Result getAllLog(@PathVariable(required = false) String page){
        System.out.println("开始查询所有日志信息");
        System.out.println("当前页面为："+page);
        if (page == null || page == "" || Integer.parseInt(page)<1){
            page="1";
        }
        PageHelper.startPage(Integer.parseInt(page),5);
        List<SysLog> logList = sysService.getAllLog();
        PageInfo<SysLog> selectPage = new PageInfo<>(logList);

        if (logList == null || logList.size()<0){
            return new Result("warn","日志查询失败",null);
        }
        Map<String, Object> result = new HashMap<String, Object>(16);
        result.put("data",selectPage.getList());
        result.put("total",selectPage.getTotal());
        return new Result("success","查询所有日志成功",result);
    }
    @DeleteMapping(value = "/delete/{id}")
    @CrossOrigin
    public Result deleteLog(@PathVariable String id){
        System.out.println("开始删除 "+id);
        boolean res = sysService.deleteLog(Integer.parseInt(id));
        if (res){
            return new Result("success","删除失败",null);
        }
        return new Result("warn","删除失败",null);
    }
    @GetMapping("/search/{content}")
    @CrossOrigin
    @RequiresRoles(logical = Logical.AND, value = {"admin"})
    public Result search(@PathVariable String content){
        System.out.println("开始搜索日志信息");
        List<SysLog> logList= sysService.search(content);
        if (logList.isEmpty()){
            return new Result("warn","未搜索到日志信息",null);
        }
        return new Result("success","成功获取到搜索的日志信息",logList);
    }

    @GetMapping("/{id}")
    @RequiresRoles(logical = Logical.AND, value = {"admin"})
    @CrossOrigin
    public Result findById(@PathVariable(value = "id") Integer id) {
        System.out.println("开始查询id= "+id);
        SysLog log = sysService.findById(id);
        if (log == null) {
            System.out.println("查询id失败");
            return new Result("warn", "查询失败(Query was fail)", null);
        }
        System.out.println("查询id成功");
        return new Result("success", "查询成功(Query was successful)", log);
    }
    @PutMapping("/updateLog")
    @CrossOrigin
    @RequiresRoles(logical = Logical.AND, value = {"admin"})
    public Result updateLog(@RequestBody SysLog log){
        System.out.println("开始更新日志信息");
        boolean res = sysService.updateLog(log);
        if(res){
            return new Result("success","修改成功",null);
        }
        return new Result("warn","修改失败",null);
    }
}

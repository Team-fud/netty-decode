package com.centerm.nettydecode.controller;

import com.centerm.nettydecode.pojo.Result;
import com.centerm.nettydecode.pojo.Terminal;
import com.centerm.nettydecode.service.TerminalService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.pagehelper.PageHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ouyangyi
 */
@RestController
@RequestMapping("/vue/terminal")
public class TerminalController {

    @Autowired
    private TerminalService terminalService;

    @GetMapping("/getAll/{page}")
    @CrossOrigin
    public Result getAllTerminals(@PathVariable(required = false) String page){
        System.out.println("当前页面为："+page);
        if (page == null || page == "" || Integer.parseInt(page)<1){
            page="1";
        }
        PageHelper.startPage(Integer.parseInt(page),5);
        List<Terminal> terminalList = terminalService.getAllTerminals();
        PageInfo<Terminal> selectPage = new PageInfo<>(terminalList);
        if (terminalList == null || terminalList.size()<0){
            return new Result("error","终端查询失败",null);
        }
        Map<String, Object> result = new HashMap<String, Object>(16);
        result.put("data",selectPage.getList());
        result.put("total",selectPage.getTotal());
        return new Result("success","查询所有终端成功",result);
    }

    @GetMapping("/search/{content}")
    @CrossOrigin
    @RequiresRoles(logical = Logical.AND, value = {"admin"})
    public Result search(@PathVariable String content){
        System.out.println("开始查询");
        List<Terminal> terminalList= terminalService.search(content);
       for(Terminal t : terminalList){
           System.out.println("查询到的终端：");
           System.out.println(t.getCateName());
       }
       if (terminalList.isEmpty()){
           return new Result("error","未搜索到终端信息",null);
       }
       return new Result("success","成功获取到搜索的终端信息",terminalList);
    }

    @DeleteMapping(value = "/delete/{ids}")
    @CrossOrigin
    public Result deleteTerminal(@PathVariable String ids){
        System.out.println("开始删除 "+ids);
        boolean res = terminalService.deleteTerminal(Integer.parseInt(ids));
        if (res){
            return new Result("success","删除成功",null);
        }
        return new Result("error","删除失败",null);
    }

    @PutMapping("/updateName")
    @CrossOrigin
    public Result updateName(@RequestBody Terminal terminal){
        System.out.println("开始更新终端信息");
        System.out.println(terminal);
        int res = terminalService.updateTerminal(terminal);
        if(res == 1){
            return new Result("success","修改成功",null);
        }
        return new Result("error","修改失败",null);
    }

    @PostMapping("/add")
    @CrossOrigin
    @RequiresPermissions(logical = Logical.AND, value = {"user:view"})
    public Result addNewTerminal(@RequestBody Terminal terminal){
        System.out.println(terminal);
        System.out.println("开始添加终端");
        if ("".equals(terminal.getCateName()) || terminal.getCateName() == null){
            return new Result("error","请输入终端名称",null);
        }
        int res = terminalService.addNewTerminal(terminal);

        if ( res == 1){
            return new Result("success","添加终端成功",null);
        }
        return new Result("error","添加终端失败",null);
    }

    @GetMapping("/{id}")
    @RequiresRoles(logical = Logical.AND, value = {"admin"})
    @CrossOrigin
    public Result findById(@PathVariable(value = "id") Integer id) {
        System.out.println("开始查询id");
        Terminal terminal = terminalService.selectByPrimaryKey(id);
        if (terminal == null) {
            return new Result("error", "查询失败(Query was fail)", null);
        }
        return new Result("success", "查询成功(Query was successful)", terminal);
    }

}

package com.centerm.nettydecode.controller;

import com.centerm.nettydecode.exception.CustomException;
import com.centerm.nettydecode.pojo.Result;
import com.centerm.nettydecode.pojo.Terminal;
import com.centerm.nettydecode.pojo.user.User;
import com.centerm.nettydecode.service.TerminalService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ouyangyi
 */
@RestController
@RequestMapping("/terminal")
public class TerminalController {

    @Autowired
    private TerminalService terminalService;

    @GetMapping("getAll")
    @CrossOrigin
    public Result getAllTerminals(){
        return new Result("success","查询所有终端成功",terminalService.getAllTerminals());
    }

    @GetMapping("search")
    @CrossOrigin
    public List<Terminal> search(String keywords){
        System.out.println("开始查询");
        List<Terminal> terminalList= terminalService.search(keywords);
       for(Terminal t : terminalList){
           System.out.println(t.getCateName());
       }
       return terminalList;
    }
    @DeleteMapping(value = "/delete/{ids}")
    @CrossOrigin
    public Result deleteTerminal(@PathVariable String ids){
        System.out.println("开始删除 "+ids);
        boolean res = terminalService.deleteTerminal(ids);
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
    public Result findById(@PathVariable("id") Integer id) {
        System.out.println("开始查询id");
        Terminal terminal = terminalService.selectByPrimaryKey(id);
        if (terminal == null) {
            throw new CustomException("查询失败(Query Failure)");
        }
        return new Result("success", "查询成功(Query was successful)", terminal);
    }

}

package com.centerm.nettydecode.controller;

import com.centerm.nettydecode.pojo.Result;
import com.centerm.nettydecode.pojo.Terminal;
import com.centerm.nettydecode.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Terminal> getAllTerminals(){
        return terminalService.getAllTerminals();
    }

    @GetMapping("search")
    public List<Terminal> search(String keywords){
        System.out.println("开始查询");
        List<Terminal> terminalList= terminalService.search(keywords);
       for(Terminal t : terminalList){
           System.out.println(t.getCateName());
       }
       return terminalList;
    }
    @DeleteMapping(value = "/delete/{ids}")
    public Result deleteTerminal(@PathVariable String ids){
        System.out.println("开始删除 "+ids);
        boolean res = terminalService.deleteTerminal(ids);
        if (res){
            return new Result("success","删除成功");
        }
        return new Result("error","删除失败");
    }
    @PutMapping("/updateName")
    public Result updateName(Terminal terminal){
        System.out.println("开始更新终端信息");
        int res = terminalService.updateTerminal(terminal);
        if(res == 1){
            return new Result("success","修改成功");
        }
        return new Result("error","修改失败");
    }
    @PostMapping("/add")
    public Result addNewTerminal(Terminal terminal){
        System.out.println("开始添加终端");
        if ("".equals(terminal.getCateName()) || terminal.getCateName() == null){
            return new Result("error","请输入终端名称");
        }
        int res = terminalService.addNewTerminal(terminal);

        if ( res == 1){
            return new Result("success","添加终端成功");
        }
        return new Result("error","添加终端失败");
    }
}

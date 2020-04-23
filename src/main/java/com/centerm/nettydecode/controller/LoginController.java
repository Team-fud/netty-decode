package com.centerm.nettydecode.controller;

import com.centerm.nettydecode.pojo.Result;
import com.centerm.nettydecode.pojo.User;
import com.centerm.nettydecode.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.Objects;

/**
 * @author Sheva
 * @date 2020/4/21 14:26
 * @description
 */

@RestController
@Api(value = "api", tags = {"登录测试"})
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;

    @CrossOrigin
    @PostMapping("/login")
    public Result login(User reqUser){
        log.info("执行登录操作");
        String username = reqUser.getUsername();
        username = HtmlUtils.htmlEscape(username);
        User user = userService.findByUsername(username);

        log.info(user.getPassword());
        if (!Objects.equals(user.getUsername(), username) || !Objects.equals(user.getPassword(), reqUser.getPassword())){
            log.info("账号密码错误...");
            return new Result("error","登录失败，账号密码错误");
        }
        log.info("登录成功...");
        return new Result("success","登录成功");
    }

}

package com.centerm.nettydecode.controller;

import com.centerm.nettydecode.constant.RedisConstants;
import com.centerm.nettydecode.exception.CustomException;
import com.centerm.nettydecode.pojo.Result;
import com.centerm.nettydecode.pojo.user.User;
import com.centerm.nettydecode.service.UserService;
import com.centerm.nettydecode.util.AesCipherUtil;
import com.centerm.nettydecode.util.JedisUtil;
import com.centerm.nettydecode.util.JwtUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Sheva
 * @date 2020/4/21 14:26
 * @description
 */

@RestController
@Api(value = "api", tags = {"登录测试"})
@Slf4j
@RequestMapping("/vue/user")
public class LoginController {

    /**
     * RefreshToken过期时间
     */
    @Value("${refreshTokenExpireTime}")
    private String refreshTokenExpireTime;

    @Autowired
    private UserService userService;

    @CrossOrigin
    @PostMapping("/login")
    public Result login(@RequestBody User reqUser, HttpServletResponse httpServletResponse) {
        log.info("执行登录操作");
        System.out.println("用户名为："+reqUser);
        String username = reqUser.getUsername();
        username = HtmlUtils.htmlEscape(username);
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new CustomException("该帐号不存在(The account does not exist.)");
        }
        String key = AesCipherUtil.deCrypto(user.getPassword());

        // 因为密码加密是以帐号+密码的形式进行加密的，所以解密后的对比是帐号+密码
        if (key.equals(user.getUsername() + reqUser.getPassword())) {
            if (JedisUtil.exists(RedisConstants.PREFIX_SHIRO_CACHE + user.getUsername())) {
                JedisUtil.delKey(RedisConstants.PREFIX_SHIRO_CACHE + user.getUsername());
            }
            //设置在线刷新时间戳
            String currentTimeMillis = String.valueOf(System.currentTimeMillis());

            JedisUtil.setObject(RedisConstants.PREFIX_SHIRO_REFRESH_TOKEN + user.getUsername(), currentTimeMillis, Integer.parseInt(refreshTokenExpireTime));
            // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
            String token = JwtUtil.sign(user.getUsername(), currentTimeMillis);
            httpServletResponse.setHeader("Authorization", token);
            httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
            System.out.println("登录成功");
            return new Result("success", "登录成功(Login Success.)", null);
        } else {
            throw new CustomException("帐号或密码错误(Account or Password Error.)");
        }
    }

    @RequestMapping("info")
    public Result getInfo(){
        Subject subject = SecurityUtils.getSubject();
        // 登录了返回true
        if (subject.isAuthenticated()) {
            return new Result("success", "您已经登录了(You are already logged in)", null);
        } else {
            return new Result("success", "你是游客(You are guest)", null);
        }
    }

    @RequestMapping("/logout")
    public Result logout(){
        Subject subject = SecurityUtils.getSubject();

        subject.logout();
        return new Result("success","用户登出",null);
    }

}

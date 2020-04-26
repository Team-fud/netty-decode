package com.centerm.nettydecode.util;

import com.centerm.nettydecode.constant.RedisConstants;
import com.centerm.nettydecode.dao.UserDao;
import com.centerm.nettydecode.exception.CustomException;
import com.centerm.nettydecode.pojo.user.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 获取当前登录用户工具类
 *
 * @author wliduo[i@dolyw.com]
 * @date 2019/3/15 11:45
 */
@Component
public class UserUtil {

    @Autowired
    public UserDao userDao;

    /**
     * 获取当前登录用户
     *
     * @param
     * @return com.wang.model.UserDto
     * @author wliduo[i@dolyw.com]
     * @date 2019/3/15 11:48
     */
    public User getUser() {
        String token = SecurityUtils.getSubject().getPrincipal().toString();
        // 解密获得Account
        String username = JwtUtil.getClaim(token, RedisConstants.ACCOUNT);
        User user = new User();
        user.setUsername(username);
        //!!!
        user = userDao.findByUsername(username);
        ///!!
        // 用户是否存在
        if (user == null) {
            throw new CustomException("该帐号不存在(The account does not exist.)");
        }
        return user;
    }

    /**
     * 获取当前登录用户Id
     *
     * @param
     * @return com.wang.model.UserDto
     * @author wliduo[i@dolyw.com]
     * @date 2019/3/15 11:48
     */
    public Integer getUserId() {
        return getUser().getId();
    }

    /**
     * 获取当前登录用户Token
     *
     * @param
     * @return com.wang.model.UserDto
     * @author wliduo[i@dolyw.com]
     * @date 2019/3/15 11:48
     */
    public String getToken() {
        return SecurityUtils.getSubject().getPrincipal().toString();
    }

    /**
     * 获取当前登录用户Account
     *
     * @param
     * @return com.wang.model.UserDto
     * @author wliduo[i@dolyw.com]
     * @date 2019/3/15 11:48
     */
    public String getUsername() {
        String token = SecurityUtils.getSubject().getPrincipal().toString();
        // 解密获得Username
        return JwtUtil.getClaim(token, RedisConstants.ACCOUNT);
    }
}

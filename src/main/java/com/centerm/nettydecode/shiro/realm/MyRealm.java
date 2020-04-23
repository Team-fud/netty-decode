package com.centerm.nettydecode.shiro.realm;

import com.centerm.nettydecode.pojo.User;
import com.centerm.nettydecode.service.UserService;
import com.centerm.nettydecode.shiro.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ouyangyi
 */
@Component
@Slf4j
public class MyRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
       String username = JwtUtil.getUsername(principalCollection.toString());
        User user = userService.findByUsername(username);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String)authenticationToken.getCredentials();
        String username = null;
        try{
            username = JwtUtil.getUsername(token);
        }catch (Exception e){
            throw new AuthenticationException("heard的token拼写错误");
        }
        if (username == null){
            log.warn("token为空");
            throw new AuthenticationException("token无效");
        }
        User user = userService.findByUsername(username);
        if (user == null){
            log.warn("用户不存在");
            throw new AuthenticationException("用户不存在");
        }
        if (!JwtUtil.verify(token,username,user.getPassword())){
            log.warn("用户账号或者密码填写错误");
            throw new AuthenticationException("用户账号或者密码填写错误");
        }
        return new SimpleAuthenticationInfo(token,token,"my_realm");
    }
}

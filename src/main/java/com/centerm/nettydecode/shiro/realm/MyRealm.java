package com.centerm.nettydecode.shiro.realm;

import com.centerm.nettydecode.constant.RedisConstants;
import com.centerm.nettydecode.dao.PermissionDao;
import com.centerm.nettydecode.dao.RoleDao;
import com.centerm.nettydecode.dao.UserDao;
import com.centerm.nettydecode.jwt.JwtToken;
import com.centerm.nettydecode.pojo.user.Permission;
import com.centerm.nettydecode.pojo.user.Role;
import com.centerm.nettydecode.pojo.user.User;
import com.centerm.nettydecode.service.UserService;
import com.centerm.nettydecode.util.JedisUtil;
import com.centerm.nettydecode.util.JwtUtil;
import com.centerm.nettydecode.util.common.StringUtil;
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

import java.util.List;

/**
 * @author ouyangyi
 */
@Component
@Slf4j
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;

    /**
     * 大坑，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        return authenticationToken instanceof JwtToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("查询用户权限");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        String username = JwtUtil.getClaim(principalCollection.toString(), RedisConstants.ACCOUNT);
        User user = new User();
        user.setUsername(username);
        // 查询用户角色
        List<Role> roleList = roleDao.findRoleByUser(user);
        for (Role role : roleList) {
            if (role != null) {
                // 添加角色
                simpleAuthorizationInfo.addRole(role.getName());
                System.out.println("目前的角色为："+role.getName());
                // 根据用户角色查询权限
                System.out.println("开始查询权限");
                List<Permission> permissionList = permissionDao.findPermissionByRole(role.getName());
                for (Permission permission : permissionList) {
                        // 添加权限
                            System.out.println("目前的权限为："+permission.getName());
                            simpleAuthorizationInfo.addStringPermission(permission.getPerCode());
                }
            }
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JwtUtil.getClaim(token, RedisConstants.ACCOUNT);
        // 帐号为空
        if (StringUtil.isBlank(username)) {
            throw new AuthenticationException("Token中帐号为空");
        }

        // 查询用户是否存在
        User user = new User();
        user.setUsername(username);
        user = userDao.findByUsername(username);
        if (user == null) {
            throw new AuthenticationException("该帐号不存在");
        }

        // 开始认证，要AccessToken认证通过，且Redis中存在RefreshToken，且两个Token时间戳一致
        try {
            if (JwtUtil.verify(token) && JedisUtil.exists(RedisConstants.PREFIX_SHIRO_REFRESH_TOKEN + username)) {
                // 获取RefreshToken的时间戳
                String currentTimeMillisRedis = JedisUtil.getObject(RedisConstants.PREFIX_SHIRO_REFRESH_TOKEN + username).toString();
                // 获取AccessToken时间戳，与RefreshToken的时间戳对比
                if (JwtUtil.getClaim(token, RedisConstants.CURRENT_TIME_MILLIS).equals(currentTimeMillisRedis)) {
                    return new SimpleAuthenticationInfo(token, token, "MyRealm");
                }
            }
        }catch (Exception e) {
            throw new AuthenticationException("Token已过期");
        }
        return null;
    }
}

package com.centerm.nettydecode.jwt;

import lombok.AllArgsConstructor;
import org.apache.shiro.authc.AuthenticationToken;

@AllArgsConstructor
public class JwtToken implements AuthenticationToken {
    /**
     * TOKEN
     */
    private String token;

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
       return token;
    }
}

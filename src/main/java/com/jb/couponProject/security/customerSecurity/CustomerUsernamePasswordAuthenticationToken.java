package com.jb.couponProject.security.customerSecurity;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * the token for our customer authentication provider
 */
public class CustomerUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken
{
    private static final long serialVersionUID = -6297334548086628577L;

    public CustomerUsernamePasswordAuthenticationToken(Object principal, Object credentials)
    {
        super(principal, credentials);
    }

    public CustomerUsernamePasswordAuthenticationToken(Object principal, Object credentials,
                                                              Collection<? extends GrantedAuthority> authorities)
    {
        super(principal, credentials, authorities);
    }
}
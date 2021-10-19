package com.jb.couponProject.security.adminSecurity;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * the token for our admin authentication provider
 */
public class AdminUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken
{
	private static final long serialVersionUID = -6897334548086628577L;

	public AdminUsernamePasswordAuthenticationToken(Object principal, Object credentials)
	{
		super(principal, credentials);
	}
	
	public AdminUsernamePasswordAuthenticationToken(Object principal, Object credentials,
													Collection<? extends GrantedAuthority> authorities)
	{
		super(principal, credentials, authorities);
	}
}


package com.jb.couponProject.security.adminSecurity;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

/**
 * the admin authentication provider
 */
public class AdminAuthenticationProvider extends DaoAuthenticationProvider {
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(AdminUsernamePasswordAuthenticationToken.class);
    }
}

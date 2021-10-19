package com.jb.couponProject.security.companySecurity;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

/**
 * the company authentication provider
 */
public class CompanyAuthenticationProvider extends DaoAuthenticationProvider {
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(CompanyUsernamePasswordAuthenticationToken.class);
    }
}

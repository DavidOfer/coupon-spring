package com.jb.couponProject.security.customerSecurity;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

/**
 * the customer authentication provider
 */
public class CustomerAuthenticationProvider extends DaoAuthenticationProvider {
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(CustomerUsernamePasswordAuthenticationToken.class);
    }
}

package com.jb.couponProject.security;

import com.jb.couponProject.JWT.JwtConfig;
import com.jb.couponProject.security.filters.CustomAuthorizationFilter;
import com.jb.couponProject.security.filters.CustomUsernameAndPasswordAuthenticationFilter;
import com.jb.couponProject.security.companySecurity.CompanyAuthenticationProvider;
import com.jb.couponProject.security.customerSecurity.CustomerAuthenticationProvider;
import com.jb.couponProject.services.AdminService;
import com.jb.couponProject.security.adminSecurity.AdminAuthenticationProvider;
import com.jb.couponProject.services.CompanyService;
import com.jb.couponProject.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * the security configuration for spring security, supplied with 3 authentication providers for 3 different datasources(3 user tables)
 */

@Configuration
@EnableWebSecurity  //configure web security
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtConfig jwtConfig;
    private final PasswordEncoder PASSWORD_ENCODER;
    private final AdminService adminService;
    private final CustomerService customerService;
    private final CompanyService companyService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .addFilter(new CustomUsernameAndPasswordAuthenticationFilter(authenticationManager(),jwtConfig,adminService,companyService,customerService))
                .addFilterAfter(new CustomAuthorizationFilter(jwtConfig), CustomUsernameAndPasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/","index","/css/*","/js/*","/media/*","/img/*","/guest/**","/login")
                .permitAll()
               // .antMatchers("/test/**").hasRole(ApplicationUserRole.ADMIN.name()) //test only for admin
                .anyRequest()
                .authenticated();
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new AdminAuthenticationProvider();
        provider.setPasswordEncoder(PASSWORD_ENCODER);
        provider.setUserDetailsService(adminService);
        return provider;
    }
    @Bean
    public DaoAuthenticationProvider customerAuthenticationProvider(){
        DaoAuthenticationProvider provider = new CustomerAuthenticationProvider();
        provider.setPasswordEncoder(PASSWORD_ENCODER);
        provider.setUserDetailsService(customerService);
        return provider;
    }
    @Bean
    public DaoAuthenticationProvider companyAuthenticationProvider(){
        DaoAuthenticationProvider provider = new CompanyAuthenticationProvider();
        provider.setPasswordEncoder(PASSWORD_ENCODER);
        provider.setUserDetailsService(companyService);
        return provider;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
        auth.authenticationProvider(customerAuthenticationProvider());
        auth.authenticationProvider(companyAuthenticationProvider());
    }
}

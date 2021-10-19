package com.jb.couponProject.JWT;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * a configuration file for our jwt util class
 */

@Configuration
public class JWTUtilConfig {
    @Bean
    public JWTUtil JWTUtil(){
        return new JWTUtil();
    }
}

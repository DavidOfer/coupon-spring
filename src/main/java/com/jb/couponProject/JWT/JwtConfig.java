package com.jb.couponProject.JWT;

import com.google.common.net.HttpHeaders;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;


/**
 * a configuration class for our jwt
 */
@Component
@Data
@NoArgsConstructor
public class JwtConfig {
    @Value("${application.jwt.secretKey}")
    private String secretKey;
    @Value("${application.jwt.tokenPrefix}")
    private String tokenPrefix;
    @Value("${application.jwt.tokenExpirationAfterMinutes}")
    private int tokenExpirationAfterMinutes;

    @Bean
    public SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String getAuthorizationHeader(){
        return HttpHeaders.AUTHORIZATION;
    }

}

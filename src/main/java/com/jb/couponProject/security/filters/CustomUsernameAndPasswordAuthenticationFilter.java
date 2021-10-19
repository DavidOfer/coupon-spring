package com.jb.couponProject.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jb.couponProject.JWT.JwtConfig;
import com.jb.couponProject.security.companySecurity.CompanyUsernamePasswordAuthenticationToken;
import com.jb.couponProject.security.customerSecurity.CustomerUsernamePasswordAuthenticationToken;
import com.jb.couponProject.security.adminSecurity.AdminUsernamePasswordAuthenticationToken;
import com.jb.couponProject.services.AdminService;
import com.jb.couponProject.services.CompanyService;
import com.jb.couponProject.services.CustomerService;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * the authentication filter for spring security. gets the client type from the requests and assigns a typed authentication token
 * in order for only  the relevant authentication provider to be called for the authentication. also provides a jwt for successful login
 */
@RequiredArgsConstructor
public class CustomUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;
    private final AdminService adminService;
    private final CompanyService companyService;
    private final CustomerService customerService;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        Authentication authenticate = null;
        try {
            UsernameAndPasswordAuthenticationRequest authenticationRequest = new ObjectMapper()
                    .readValue(request.getInputStream(), UsernameAndPasswordAuthenticationRequest.class);
            String clientType=authenticationRequest.getGrantedAuthorities();
            switch(clientType)
            {
                case "ROLE_ADMIN":
                    authenticate = new AdminUsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    );
                    break;
                case "ROLE_CUSTOMER":
                    authenticate = new CustomerUsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    );
                    break;
                case "ROLE_COMPANY":
                    authenticate = new CompanyUsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    );
                    break;
            }

        } catch (IOException err) {
            throw new RuntimeException(err);
        }
        return authenticationManager.authenticate(authenticate);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //use the web site for good key 512bytes : https://www.allkeysgenerator.com/Random/Security-Encryption-Key-Generator.aspx

        //using JWT library
        String role=authResult.getAuthorities().iterator().next().toString();
        String username= authResult.getName();
        int id=0;
        switch (role)
        {
            case "ROLE_ADMIN":
                id=adminService.findIdByUsername(username);
                break;
            case "ROLE_COMPANY":
                id=companyService.findIdByUsername(username);
                break;
            case "ROLE_CUSTOMER":
                id=customerService.findIdByUsername(username);
                break;
            default:
                System.out.println("user authenticated but role wasnt found");
        }

        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("id",id)
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(Date.valueOf(LocalDate.now()))
                .setExpiration(new java.util.Date(System.currentTimeMillis()+ 1000L * 60 *jwtConfig.getTokenExpirationAfterMinutes()))
                .signWith(jwtConfig.getSecretKey())
                .compact();

        //send token to client
        response.addHeader(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix() + token); //for jwt token
    }
}

package com.jb.couponProject.security.filters;

import com.google.common.base.Strings;
import com.jb.couponProject.JWT.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * an authorization filter for spring security, if no token in the request forwards the request to the filter chain, otherwise checks the validity of the token
 * and refreshes the token for the response
 */
@RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    //execute the filter only once for every single request comming from the client
    private final JwtConfig jwtConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());
        //if we have incorrect token
        if (Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())){
            filterChain.doFilter(request,response);
            return;
        }

        //JWT -> Jeson Web Token   JWS -> Json Web (token) Signed done by .compact()
        String token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), "");
        try{
            Jws<Claims> clamisJws = Jwts.parser().setSigningKey(jwtConfig.getSecretKey()).parseClaimsJws(token);
            Claims body = clamisJws.getBody();
            String username = body.getSubject();
            var authorities = (List<Map<String,String>>) body.get("authorities");
            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                    .map(item -> new SimpleGrantedAuthority(item.get("authority")))
                    .collect(Collectors.toSet());
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username,null, simpleGrantedAuthorities
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //Refresh token in the response
            String newToken = Jwts.builder()
                    .setSubject(body.getSubject())
                    .claim("id",body.get("id"))
                    .claim("authorities", body.get("authorities"))
                    .setIssuedAt(Date.valueOf(LocalDate.now()))
                    .setExpiration(new java.util.Date(System.currentTimeMillis()+ 1000L* 60 *jwtConfig.getTokenExpirationAfterMinutes()))
                    .signWith(jwtConfig.getSecretKey())
                    .compact();
            System.out.println("refreshing token");
            response.addHeader("Authorization","Bearer "+newToken);

        } catch (JwtException err){
//            throw new IllegalStateException(String.format("Token %s can not be trusted",token));
            System.out.println("unauthorized token access attempt");
        }
        filterChain.doFilter(request,response);
    }
}

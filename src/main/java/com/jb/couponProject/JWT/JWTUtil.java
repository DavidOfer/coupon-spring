package com.jb.couponProject.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * a util class for our jwt
 */

@RequiredArgsConstructor
public class JWTUtil {
    @Autowired
    private JwtConfig jwtConfig;
//    public String getSubject(String token){
//        String noHeaderToken = token.replace(jwtConfig.getTokenPrefix(), "");
//        Jws<Claims> clamisJws = Jwts.parser().setSigningKey(jwtConfig.getSecretKey()).parseClaimsJws(noHeaderToken);
//        Claims body = clamisJws.getBody();
//        return body.getSubject();
//    }
    public int getId(String token){
        String noHeaderToken = token.replace(jwtConfig.getTokenPrefix(), "");
        Jws<Claims> clamisJws = Jwts.parser().setSigningKey(jwtConfig.getSecretKey()).parseClaimsJws(noHeaderToken);
        Claims body = clamisJws.getBody();
        return body.get("id",Integer.class);
    }
}

package com.example.zuzex.JWTgenerator;

import java.security.Key;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
@Component
@Getter
public class JwtGenerator {
    private Key key ;


    public JwtGenerator( @Value("${JWT.secKey}") String secKey) {
        this.key = Keys.hmacShaKeyFor(secKey.getBytes());
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .signWith(key)
                .compact();
    }
    public void ValidateToken(String jwtToken){

    }
    public String parseToken(String jwtToken) {
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        String username = claims.getSubject();
        return username;
    }


}

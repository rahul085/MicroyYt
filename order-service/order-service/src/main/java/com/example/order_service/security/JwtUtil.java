package com.example.order_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
    private final Key secretKey;

    public JwtUtil(@Value("${application.security.jwt.secret-key}") String secretKey){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        if(keyBytes.length<32){
            throw new IllegalArgumentException("The token should be at least 32 bytes long!");
        }
        this.secretKey= Keys.hmacShaKeyFor(keyBytes);
    }
    @Value("${application.security.jwt.expirationMs}")
    private long expirationMs;

    public String extractUsername(String token){
        return extractAllClaims(token, Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractAllClaims(token,Claims::getExpiration);
    }


    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(Date.from(Instant.now()));
    }

    public boolean validateToken(String token){
        return !isTokenExpired(token);
    }
    private <T> T extractAllClaims(String token , Function<Claims,T> claimsResolver){
        Claims claims=parseClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims parseClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

    }
}

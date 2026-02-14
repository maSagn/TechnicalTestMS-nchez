
package com.TechnicalTest.MSanchezTechinicalTest.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    
    private final Key key = Keys.hmacShaKeyFor("12345678901234567890123456789012".getBytes());

    public String generateToken(String tax_id) {
        String jti = UUID.randomUUID().toString();
        return Jwts.builder()
                .setSubject(tax_id)
                .setId(jti)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86_400_000)) // 1 día
                .signWith(key)
                .compact();
    }

    public Jws<Claims> validateToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }
    
}

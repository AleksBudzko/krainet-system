package org.petprojects.authservice.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}") private String secret;
    @Value("${jwt.expiration-ms}") private long expirationMs;

    public String generateToken(UserDetails userDetails) {
        Date now = new Date(), exp = new Date(now.getTime() + expirationMs);
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities())
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token, UserDetails ud) {
        try {
            var claims = Jwts.parser().setSigningKey(secret)
                    .parseClaimsJws(token).getBody();
            return claims.getSubject().equals(ud.getUsername())
                    && !claims.getExpiration().before(new Date());
        } catch (JwtException|IllegalArgumentException e) {
            return false;
        }
    }
}

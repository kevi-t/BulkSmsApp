package com.project.messageapp.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtTokenService {
    private static final String SECRET_KEY = "7638792F423F4528482B4D6251655368566D597133743677397A24432646294A";
    private static final long TOKEN_EXPIRATION_DURATION_MS = 3600000; // 1 hour

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_DURATION_MS))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        //System.out.println("Received Token: " +token);
        try {
            return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
        }
        catch (ExpiredJwtException expiredJwtException) {
            log.error("JWT token expired: {}", expiredJwtException.getMessage());
            throw expiredJwtException; // rethrow the exception for higher-level handling
        }
        catch (UnsupportedJwtException | MalformedJwtException  jwtException) {
            log.error("Error parsing JWT token: {}", jwtException.getMessage());
            throw jwtException; // rethrow the exception for higher-level handling
        }
        catch (PrematureJwtException | InvalidClaimException  claimException) {
            log.error("Invalid claim in JWT token: {}", claimException.getMessage());
            throw claimException; // rethrow the exception for higher-level handling
        }
    }
}
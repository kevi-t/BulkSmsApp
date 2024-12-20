package com.project.messageapp.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class JwtTokenUtil {
    private static  final String secret = "7638792F423F4528482B4D6251655368566D597133743677397A24432646294A";
    private static final long TOKEN_EXPIRATION_DURATION_MS = 3600000; // 1 hour

    private Key getSignInKey(){
        byte[] keyBytes= Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public String extractUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }
    public String extractOccupation(String token){
        return extractClaim(token,Claims::getAudience);
    }
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_DURATION_MS))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
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
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
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
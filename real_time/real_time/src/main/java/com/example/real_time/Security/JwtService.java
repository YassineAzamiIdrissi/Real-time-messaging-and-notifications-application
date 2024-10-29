package com.example.real_time.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static java.lang.System.currentTimeMillis;

@Service
public class JwtService {
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.jwtExpiration}")
    private long jwtExpiration;


    public boolean isTokenValid(String token, UserDetails userDetails) {
        String extractedSub = extractUsername(token);
        return !isTokenExpired(token) && extractedSub.equals(userDetails.getUsername());
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractSpecificClaim(token, Claims::getExpiration);
    }

    public String extractUsername(String token) {
        return extractSpecificClaim(token, Claims::getSubject);
    }

    private <T> T extractSpecificClaim
            (String token, Function<Claims, T> resolver) {
        Claims allClaims = extractAllClaims(token);
        return resolver.apply(allClaims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(
                        generateSignInKey()
                ).build().
                parseClaimsJws(token).
                getBody();
    }

    private String generateToken(UserDetails userDetails) {
        return generateToken(userDetails, new HashMap<>());
    }

    private String generateToken(UserDetails userDetails,
                                 Map<String, Object> claims) {
        return buildToken(userDetails, claims, jwtExpiration);
    }

    private String buildToken(UserDetails userDetails,
                              Map<String, Object> claims,
                              long jwtExp) {
        var auths = userDetails.getAuthorities().
                stream().map(GrantedAuthority::getAuthority).
                toList();
        return Jwts.builder().
                setSubject(userDetails.getUsername()).
                setIssuedAt(new Date(currentTimeMillis())).
                setExpiration(new Date(currentTimeMillis() + jwtExp)).
                setClaims(claims).
                signWith(generateSignInKey()).
                claim("authorities", auths).
                compact();
    }

    private Key generateSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

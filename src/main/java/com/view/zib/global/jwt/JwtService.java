package com.view.zib.global.jwt;

import com.view.zib.domain.auth.domain.Jwt;
import com.view.zib.domain.auth.enums.TokenType;
import com.view.zib.domain.user.entity.UserEntity;
import com.view.zib.global.exception.InvalidAccessTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
@Service
public class JwtService {

    @Value("${jwt.access-token.secret-key}")
    private String secretKey;

    @Value("${jwt.access-token.expiration}")
    private long jwtExpiration;

    public Jwt generateToken(UserEntity userEntity) {
        String accessToken = generateAccessToken(userEntity);

        return Jwt.builder()
                .accessToken(accessToken)
                .tokenType(TokenType.BEARER)
                .expiresIn(jwtExpiration)
                .build();
    }

    public String generateAccessToken(UserEntity userEntity) {
        return generateAccessToken(new HashMap<>(), userEntity);
    }

    public String generateAccessToken(
            Map<String, Object> extraClaims,
            UserEntity userEntity
    ) {
        return buildToken(extraClaims, userEntity, jwtExpiration);
    }

    public String generateRefreshToken(UserEntity userEntity) {
        return buildToken(new HashMap<>(), userEntity, Long.MAX_VALUE);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserEntity userEntity,
            long expiration
    ) {
        var now = System.currentTimeMillis();
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userEntity.getEmail()) // our Spring Security's email equals email
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserEntity userEntity) {
        final String email = extractUsername(token);
        return (email.equals(userEntity.getEmail())) && !isTokenExpired(token);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String email = extractUsername(token);
        return (email.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    private Claims extractAllClaims(String token) {

        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.error(e.getMessage());
            // TODO: jwt 만료시 로그만 찍고 stacktrace는 찍지 않도록 수정
            throw new RuntimeException();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new InvalidAccessTokenException();
        }
    }

    /**
     * our Spring Security's email equals email
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Long extractPartnerId(String token) {
        return extractClaim(token, claims -> claims.get("partnerId", Long.class));
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getAccessToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        } else {
            throw new InvalidAccessTokenException();
        }
    }

    public String getAccessTokenFrom(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        return getAccessToken(header);
    }

    public long getExpiresIn() {
        return jwtExpiration / 1000 - 1;
    }
}

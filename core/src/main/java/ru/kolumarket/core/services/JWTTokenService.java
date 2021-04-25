package ru.kolumarket.core.services;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.kolumarket.core.domain.UserInfo;
import ru.kolumarket.core.interfaces.ITokenService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JWTTokenService implements ITokenService {

    @Value("${kolumarket.jwt.secret}")
    private String jwtSecret;

    @Value("${kolumarket.jwt.expiration}")
    private String jwtExpiration;

    @Override
    public Long getJwtExpiration() {
        return Long.valueOf(jwtExpiration);
    }

    @Override
    public String generateToken(UserInfo user) {
        Date expirationDate = Date.from(Instant.now().plus(Long.valueOf(jwtExpiration), ChronoUnit.HOURS));

        String compactTokenString = Jwts.builder()
                .claim("id", user.getUserId())
                .claim("role", user.getRole())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        return "Bearer " + compactTokenString;
    }

    @Override
    public UserInfo parseToken(String token) throws ExpiredJwtException {
        Jws<Claims> jwsClaims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token);

        Long userId = jwsClaims.getBody()
                .get("id", Long.class);

        String role = jwsClaims.getBody()
                .get("role", String.class);

        return UserInfo.builder()
                .userId(userId)
                .role(role)
                .build();
    }
}

package com.project.coalba.domain.auth.token;

import com.project.coalba.domain.auth.entity.User;
import com.project.coalba.domain.auth.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;

@Slf4j
@Component
public class AuthTokenManager {
    private final UserRepository userRepository;
    private final Key key;

    @Value("${app.auth.accessTokenExpiry}")
    private Long accessTokenExpiry;

    @Value("${app.auth.refreshTokenExpiry}")
    private Long refreshTokenExpiry;

    private static final String USER_ID_KEY = "userId";

    @Autowired
    public AuthTokenManager(@Value("${app.auth.tokenSecret}") String secretKey, UserRepository userRepository) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.userRepository = userRepository;
    }

    public String createAccessToken(String providerId, Long userId) {
        Date expiryDate = getExpiryDate(accessTokenExpiry);
        return createToken(providerId, userId, expiryDate);
    }

    public String createRefreshToken() {
        Date expiryDate = getExpiryDate(refreshTokenExpiry);
        return createToken(expiryDate);
    }

    private Date getExpiryDate(Long expiry) {
        return new Date(System.currentTimeMillis() + expiry);
    }

    private String createToken(String providerId, Long userId, Date expiry) {
        return Jwts.builder()
                .setSubject(providerId)
                .signWith(key, SignatureAlgorithm.HS256)
                .setIssuedAt(new Date())
                .setExpiration(expiry)
                .claim(USER_ID_KEY, userId)
                .compact();
    }

    private String createToken(Date expiry) {
        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setIssuedAt(new Date())
                .setExpiration(expiry)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        if (validate(token)) {
            Claims claims = getTokenClaims(token);
            Long userId = claims.get(USER_ID_KEY, Long.class);
            Optional<User> userOptional = userRepository.findById(userId);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                Collection<? extends GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getCode()));
                UserPrincipal principal = UserPrincipal.create(user);
                return new UsernamePasswordAuthenticationToken(principal, token, authorities);
            }
        }
        return null;
    }

    public boolean validate(String token) {
        return this.getTokenClaims(token) != null;
    }

    public Claims getTokenClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SecurityException e) {
            log.error("Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            log.error("JWT token compact of handler are invalid");
        } catch (JwtException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public Claims getExpiredTokenClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token.");
            return e.getClaims();
        } catch (SecurityException e) {
            log.error("Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            log.error("JWT token compact of handler are invalid");
        } catch (JwtException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}

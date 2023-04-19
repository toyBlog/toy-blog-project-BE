package com.toy.blog.auth.config;

import com.toy.blog.auth.exception.login.ExpiredTokenException;
import com.toy.blog.auth.exception.login.InvalidTokenException;
import com.toy.blog.auth.model.TokenResponseDto;
import com.toy.blog.domain.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
public class TokenProvider {

    private static final String TOKEN_TYPE = "Bearer";

    private static final String AUTHORITY_KEY = "auth";

    private static final long ACCESS_TOKEN_EXPIRE_TIME_MILLIS = 24L * 60L * 60L * 1000L;

    private static final long REFRESH_TOKEN_EXPIRE_TIME_MILLIS = 30L * 24L * 60L * 60L * 1000L;

    private Key key;

    @Value("${security.jwt.key}")
    String secretKey;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public TokenResponseDto generateTokenResponse(User user, boolean tmpPasswordUsed, boolean isJoin) {
        long now = new Date().getTime();
        return TokenResponseDto.builder()
                .tokenType(TOKEN_TYPE)
                .accessToken(generateToken(user, new Date(now + ACCESS_TOKEN_EXPIRE_TIME_MILLIS)))
                .expiresIn((now + ACCESS_TOKEN_EXPIRE_TIME_MILLIS) / 1000)
                .refreshToken(generateRefreshToken(user, new Date(now + REFRESH_TOKEN_EXPIRE_TIME_MILLIS)))
                .refreshTokenExpiresIn((now + REFRESH_TOKEN_EXPIRE_TIME_MILLIS) / 1000)
                .tmpPasswordUsed(tmpPasswordUsed)
                .isJoin(isJoin)
                .build();
    }

    public TokenResponseDto generateTokenOnlyResponse(User user) {
        long now = new Date().getTime();
        return TokenResponseDto.builder()
                .tokenType(TOKEN_TYPE)
                .accessToken(generateToken(user, new Date(now + ACCESS_TOKEN_EXPIRE_TIME_MILLIS)))
                .expiresIn((now + ACCESS_TOKEN_EXPIRE_TIME_MILLIS) / 1000)
                .build();
    }

    private String generateToken(User user, Date expDate) {
        return Jwts.builder()
                .setIssuer("blog")
                .setSubject(user.getId().toString())
                //.claim(AUTHORITY_KEY, user.getAuthority())
                .setExpiration(expDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    private String generateRefreshToken(User user, Date expDate) {
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setExpiration(expDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // todo: 예외 설정(우성)
    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);
        if (ObjectUtils.isEmpty(claims.get(AUTHORITY_KEY))) {
            //throw new InvalidTokenException(token);
        }

        Collection<? extends GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(claims.get(AUTHORITY_KEY).toString()));
        return new UsernamePasswordAuthenticationToken(claims.getSubject(), "", authorities);
    }

    public boolean isValidToken(String token) {
        parseClaims(token);
        return true;
    }

    public Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException(token);
        } catch (Exception e) {
            throw new InvalidTokenException(token);
        }
    }

    public String getJwtToken(HttpServletRequest request) {
        String jwtToken = Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .orElseThrow(NullPointerException::new);

        if (!StringUtils.hasText(jwtToken) || !jwtToken.startsWith(TOKEN_TYPE)) {
            throw new InvalidTokenException(jwtToken);
        }

        return jwtToken.substring(7);
    }
}
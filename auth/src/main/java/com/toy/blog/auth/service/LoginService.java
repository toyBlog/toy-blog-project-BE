package com.toy.blog.auth.service;

import com.toy.blog.auth.config.TokenProvider;
import com.toy.blog.auth.exception.UnauthorizedException;
import com.toy.blog.auth.model.TokenResponseDto;
import com.toy.blog.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;


    public TokenResponseDto generateTokenResponse(User user) {
        return tokenProvider.generateTokenResponse(user, true, false);
    }

    public Long getLoginUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            if (UsernamePasswordAuthenticationToken.class.isAssignableFrom(
                    Optional.ofNullable(authentication).orElseThrow(UnauthorizedException::new).getClass())) {
                return Long.valueOf(authentication.getName());
            }
        } catch (UnauthorizedException e) {
            throw e;
        }
        return null;
    }


}

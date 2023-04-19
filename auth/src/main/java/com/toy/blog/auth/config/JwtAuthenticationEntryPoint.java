package com.toy.blog.auth.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JwtAuthenticationEntryPoint 정리
 *
 *  - AuthenticationEntryPoint ?
 *    - Spring Security 에서 인증(Authentication) 에 필요 리소스에 인증되지 않은
 *    사용자가 접근 했을 때 호출되는 헨들러
 *    - 일반적으로 로그인 페이지로 리다이렉트 하거나 인증 오류 메시지를 반환
 *    - 제공되는 메소드
 *      1. commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException):
 *      인증 오류가 발생한 경우 호출되는 메소드, 이 메소드에서는 인증 오류에 대한 응답(response)을 처리
 *
 */

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException authException) throws IOException {
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

}
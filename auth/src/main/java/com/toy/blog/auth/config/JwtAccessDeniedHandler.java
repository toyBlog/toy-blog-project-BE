package com.toy.blog.auth.config;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JwtAccessDeniedHandler 정리
 *
 *  - AccessDeniedHandler ?
 *    - Spring Security 에서 인가가 실패 되었을 때 호출되는 헨들러
 *    - 인증(Authentication) 은 성고했지만, 권한(Authorization) 이 없는 경우 AccessDeniedException 발생
 *    시키기 위해 AccessDeniedHandler 호출
 *    - 제공되는 메소드
 *      1. handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException):
 *      AccessDeniedException 이 발생한 경우 호출되는 메소드, 이 메소드에서는 접근 거부에 대한 응답(response)을 처리
 *
 */

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}
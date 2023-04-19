package com.toy.blog.auth.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JwtFilter 정리
 * <p>
 * - OncePerRequestFilter ?
 * - Spring Security 에서 제공되는 필터 중 하나
 * - 각 요청당 한번만 실행되도록 보장
 * - Filter 를 조금 확장 시킨 GenericFilterBean 있는데 필터가 두번 실행되는 현상 때문에 문제가 있음
 * - 그래서 OncePerRequestFilter 가 등장 OncePerRequestFilter 는 GenericFilterBean 을 상속 받고있음
 * <p>
 * - 사용하는 이유??
 * 1. 요청당 한 번만 실행되도록 보장: 여러 번 실행되는 경우에 발생하는 부작용을 방지하기 위해서입니다.
 * 예를 들어, 인증 필터에서는 인증 정보를 설정하고, 이 정보를 사용하여 권한 검사를 수행합니다.
 * 여러 번 실행되는 경우, 같은 인증 정보를 반복해서 설정하거나 권한 검사를 중복해서 수행할 수 있습니다.
 * 2. 필터 체인의 다음 필터로 이동: OncePerRequestFilter 는 필터 체인의 다음 필터로 이동하여 요청을 처리합니다.
 * 이를 통해 필터 체인의 다른 필터들이 요청을 처리하도록 할 수 있습니다.
 * <p>
 * - 제공되는 메소드
 * 1. doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain):
 * 각 요청당 한 번 실행되는 메소드, 이 메소드에서는 요청(request)과 응답(response)을 처리하고,
 * 필터 체인(filterChain)의 다음 필터를 호출
 */

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            String jwtToken = tokenProvider.getJwtToken(request);

            if (!ObjectUtils.isEmpty(jwtToken) && tokenProvider.isValidToken(jwtToken)) {
                Authentication authentication = tokenProvider.getAuthentication(jwtToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ignored) {
        }
        filterChain.doFilter(request, response);
    }
}
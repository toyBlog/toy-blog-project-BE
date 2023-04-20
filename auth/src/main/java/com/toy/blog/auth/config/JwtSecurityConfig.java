package com.toy.blog.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * JwtSecurityConfig 정리
 * <p>
 * - SecurityConfigurerAdapter ?
 * - Spring Security 를 구성하는 클래스
 * - 기본적인 보안 구성을 제공하고 사용자가 지정 보안 구성을 추가 할 수 있음
 * <p>
 * - 사용하는 이유
 * 1. 기본적인 보안 구성 제공: Spring Security는 기본적인 보안 구성을 제공합니다.
 * 예를 들어, 인증을 위한 폼 기반 로그인, 권한 부여를 위한 Role 기반 접근 제어 등이 있습니다.
 * SecurityConfigurerAdapter 는 이러한 기본적인 보안 구성을 제공하며,
 * 개발자는 이를 기반으로 필요한 보안 구성을 추가하거나 수정할 수 있습니다.
 * 2. 사용자 지정 보안 구성 추가: SecurityConfigurerAdapter 를 사용하면 개발자는 사용자 지정 보안 구성을 추가할 수 있습니다.
 * 예를 들어, 인증 방식을 변경하거나, 권한 검사를 위한 사용자 지정 AccessDecisionManager 를 구현할 수 있습니다.
 * <p>
 * - 제공되는 메소드
 * 1. configure(T builder): 보안 구성을 추가 또는 수정합니다. T는 SecurityBuilder 를 구현한 클래스입니다.
 * 예를 들어, HttpSecurity 를 구성하려면 configure(HttpSecurity http) 메소드를 사용합니다.
 */

@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final TokenProvider tokenProvider;

    @Override
    public void configure(HttpSecurity http) {
        JwtFilter jwtFilter = new JwtFilter(tokenProvider);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
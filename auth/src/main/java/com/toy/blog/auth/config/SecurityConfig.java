package com.toy.blog.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * SecurityConfig 정리
 * <p>
 * - WebSecurityConfigurerAdapter ?
 * - 웹 보안 구성을 쉽게 구현할 수 있도록 도와주는 클래스
 * - 주로 configure 메소드는 HTTP 보안 구성을 위해 사용됩니다.
 * - 이 메소드를 사용하여 인증, 권한 부여, 로그인, 로그아웃 등을 설정할 수 있습니다.
 */

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(
                        // swagger
                        "/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**", "/actuator/**"
                );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
//                .antMatchers("/admin/**").hasRole("ADMIN") //TODO:: 개발중 임시 주석
                .anyRequest().permitAll()
                .and()
                .apply(new JwtSecurityConfig(tokenProvider));
    }
}
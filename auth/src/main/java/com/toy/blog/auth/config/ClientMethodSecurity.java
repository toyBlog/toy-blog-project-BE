package com.toy.blog.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * ClientMethodSecurity 정리
 * <p>
 * - @EnableGlobalMethodSecurity
 * - 메소드 단위 보안 적용을 위한 어노테이션
 * - 스프링 시큐리티에서 제공하는 애플리케이션 보안 기능
 * - @PreAuthorize, @PostAuthorize, @Secured 어노테이션을 이용하여 메소드 보안 가능
 * - prePostEnabled = true 를 사용한 이유
 * - @PreAuthorize와 @PostAuthorize 어노테이션 활성화를 위하여
 * - securedEnabled = true 를 사용한 이유
 * - @Secured 어노테이션 활성화를 위하여
 * -------------------------------------------------------------------------
 * <p>
 * - GlobalMethodSecurityConfiguration 정리
 * - 메소드 보안을 구성하기 위한 추상 클래스
 * - createExpressionHandler 를 사용한 이유
 * - MethodSecurityExpressionHandler 를 사용하기 위하여
 * - MethodSecurityExpressionHandler 란 메소드 보안에서 사용되는 SpEL
 * (Spring Expression Language) 표현식을 처리하는 핸들러
 * - DefaultMethodSecurityExpressionHandler 쓰는 이유
 * - SpEL 표현식에서 사용할 수 있는 보안 연산 제공 (hasRole(), hasAuthority(),
 * hasAnyRole(), hasIpAddress() 등)
 * <p>
 * -------------------------------------------------------------------------
 * - expressionHandler.setPermissionEvaluator(new ClientPermissionExpression());
 * - Strategy used in expression evaluation to determine whether a user has a
 * permission or permissions for a given domain object.
 * (주어진 도메인 객체에 대해 사용자가 특정 권한 또는 권한 집합을 가지고 있는지 여부를 결정하는
 * 표현식 평가에 사용되는 전략입니다.)
 */

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class ClientMethodSecurity extends GlobalMethodSecurityConfiguration {

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(new ClientPermissionExpression());
        return expressionHandler;
    }
}

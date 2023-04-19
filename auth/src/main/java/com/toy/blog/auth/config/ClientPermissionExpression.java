package com.toy.blog.auth.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

/**
 * ClientPermissionExpression 정리
 *
 * - PermissionEvaluator ?
 *   - Spring Security 에서 도메인 객체에 대한 권한 검사를 수행하기 위한 인터페이스
 *   - hasPermission() 메소드를 제공하며, 이를 구현함으로써 사용자가 특정 도메인 객체
 *   에 대한 특정 권한을 가지고 있는지 체크 가능
 *     - hasPermission 은 세 개의 매개 변수를 받는다.
 *       1. 인증 객체 ( Authentication authentication )
 *       2. 검사 대상 도메인 객체 ( Object targetDomainObject )
 *       3. 권한 이름<String> ( Object permission )
 *
 */

@Slf4j
public class ClientPermissionExpression implements PermissionEvaluator {
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        // todo: 권한체크(유성)
        log.info("authentication: {}, targetDomainObject:{}, permission: {}", authentication, targetDomainObject, permission);
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        log.info("authentication: {}, targetId:{}, targetType: {}, permission: {}", authentication, targetId, targetType, permission);
        return false;
    }
}

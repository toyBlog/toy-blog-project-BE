package com.toy.blog.auth.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

@Slf4j
public class ClientPermissionExpression implements PermissionEvaluator {
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        // todo: 권한 체크
        log.info("authentication: {}, targetDomainObject:{}, permission: {}", authentication, targetDomainObject, permission);

        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        log.info("authentication: {}, targetId:{}, targetType: {}, permission: {}", authentication, targetId, targetType, permission);
        return false;
    }
}

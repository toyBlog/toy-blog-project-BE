package com.toy.blog.auth.value;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthError {

    EXPIRED_TOKEN(9000, "토큰이 만료되었습니다."),
    INVALID_TOKEN(9001, "잘못된 토큰입니다."),
    UNAUTHORIZED(9002, "로그인이 필요합니다."),
    PERMISSION_DENIED(9003, "권한이 없습니다."),
    NOT_MATCHED_AUTH_CODE(2002, "인증번호가 틀립니다."),
    ALREADY_EXIST_USER_TEL(2000, "해당 전화번호로 이미 가입된 계정이 있습니다."),
    ;

    final Integer code;

    final String message;
}

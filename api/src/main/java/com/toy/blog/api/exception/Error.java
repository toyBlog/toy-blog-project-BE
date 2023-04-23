package com.toy.blog.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * [ 1000단위 ] - 오류의 범위
 *  1000 : 요청 성공
 *  2 : Request 오류
 *  3 : Response 오류
 *  4 : DB, Server 오류
 *
 * [ 100단위 ] - 오류 도메인
 *  0 : 공통 오류
 *  1 : user 오류
 *  2 : article 오류
 *  3 : articleImage 오류
 *  4 : liked 오류
 *  5 : userFriend 오류
 *
 * [10단위] - 오류 HTTP Method
 *  0~19 : Common
 *  20~39 : GET
 *  40~59 : POST
 *  60~79 : PATCH
 *  80~99 : else
 *
 *  [1 단위] - 그외 오류의 고유 식별자
 *          - 순서대로 설정해주면 됨
 */
@Getter
@AllArgsConstructor
public enum Error {

    ACCESS_DENIED_EXCEPTION(2100, "access denied exception"),
    NOT_FOUND_ACTIVE_USER(2121, "user not found"),
    ALREADY_EXIST_USER(2140, "already exist user"),
    INVALID_PASSWORD(2141, "invalid password"),
    NOT_FOUND_ARTICLE(2220, "not found article"),
    BLOCKED_USER_FRIEND(2521, "blocked friend"),
    NOT_FOUND_USER_FRIEND(2522, "user-friend not found"),

    INTERNAL_SERVER_ERROR(9999, "internal server error");





    private final Integer code;
    private final String message;
}

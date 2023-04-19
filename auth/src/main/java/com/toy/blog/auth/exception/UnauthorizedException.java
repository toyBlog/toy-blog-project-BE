package com.toy.blog.auth.exception;

import lombok.Getter;

public class UnauthorizedException extends RuntimeException {

    @Getter
    private Error error;

}

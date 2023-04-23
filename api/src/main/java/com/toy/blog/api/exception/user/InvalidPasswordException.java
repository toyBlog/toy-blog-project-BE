package com.toy.blog.api.exception.user;

import com.toy.blog.api.exception.Error;

public class InvalidPasswordException extends UserException {

    public InvalidPasswordException() {
        super(Error.INVALID_PASSWORD);
    }
}

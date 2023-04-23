package com.toy.blog.api.exception.user;

import com.toy.blog.api.exception.Error;

public class AlreadyExistUserException extends UserException {

    public AlreadyExistUserException() {
        super(Error.ALREADY_EXIST_USER);
    }
}

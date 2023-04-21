package com.toy.blog.api.exception.user;

import com.toy.blog.api.exception.Error;

public class NotFoundUserException extends UserException{

    public NotFoundUserException() {
        super(Error.NOT_FOUND_ACTIVE_USER);
    }
}

package com.toy.blog.api.exception.user;

import com.toy.blog.api.exception.Error;
import lombok.Getter;

public class UserException extends RuntimeException{

    @Getter
    final Error error;

    public UserException (Error error) {
        super(error.getMessage());
        this.error = error;
    }
}

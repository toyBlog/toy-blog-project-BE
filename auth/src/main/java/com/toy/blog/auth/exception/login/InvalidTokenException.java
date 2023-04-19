package com.toy.blog.auth.exception.login;

import com.toy.blog.auth.exception.UserException;
import com.toy.blog.auth.value.AuthError;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvalidTokenException extends UserException {

    String token;

    public InvalidTokenException(String token) {
        super(AuthError.INVALID_TOKEN);
    }

}

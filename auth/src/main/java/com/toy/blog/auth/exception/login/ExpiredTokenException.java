package com.toy.blog.auth.exception.login;

import com.toy.blog.auth.exception.UserException;
import com.toy.blog.auth.value.AuthError;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExpiredTokenException extends UserException {

    @Getter
    String token;

    public ExpiredTokenException(String token) {
        super(AuthError.EXPIRED_TOKEN);
        this.token = token;
    }

}

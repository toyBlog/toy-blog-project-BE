package com.toy.blog.auth.exception.login;

import com.toy.blog.auth.exception.UserException;
import com.toy.blog.auth.value.AuthError;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginException extends UserException implements Serializable {

    public LoginException(AuthError authError) {
        super(authError);
    }

}

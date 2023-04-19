package com.toy.blog.auth.exception.join;

import com.toy.blog.auth.exception.UserException;
import com.toy.blog.auth.value.AuthError;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class JoinException extends UserException implements Serializable {

    public JoinException(AuthError authError) {
        super(authError);
    }

}

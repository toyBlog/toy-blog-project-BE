package com.toy.blog.auth.exception.join;


import com.toy.blog.auth.value.AuthError;

public class NotMatchedAuthCodeException extends JoinException {

    public NotMatchedAuthCodeException() {
        super(AuthError.NOT_MATCHED_AUTH_CODE);
    }

}

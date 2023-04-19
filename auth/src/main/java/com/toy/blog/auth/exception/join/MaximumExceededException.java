package com.toy.blog.auth.exception.join;

import com.toy.blog.auth.value.AuthError;

public class MaximumExceededException extends JoinException {

    public MaximumExceededException(AuthError authError) {
        super(authError);
    }

}

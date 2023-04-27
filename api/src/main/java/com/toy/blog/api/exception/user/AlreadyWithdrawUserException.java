package com.toy.blog.api.exception.user;

import com.toy.blog.api.exception.Error;

public class AlreadyWithdrawUserException extends UserException {

    public AlreadyWithdrawUserException() {
        super(Error.ALREADY_WITHDRAW_USER);
    }
}

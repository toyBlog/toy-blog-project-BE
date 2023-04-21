package com.toy.blog.api.exception.user_friend;

import com.toy.blog.api.exception.Error;
import lombok.Getter;

public class UserFriendException extends RuntimeException {

    @Getter
    final Error error;

    public UserFriendException (Error error) {
        super(error.getMessage());
        this.error = error;
    }

}

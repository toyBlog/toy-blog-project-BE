package com.toy.blog.api.exception.user_friend;

import com.toy.blog.api.exception.Error;

public class SameIdUserFriendException extends UserFriendException{

    public SameIdUserFriendException() {
        super(Error.SAME_ID_USER_FRIEND);
    }
}

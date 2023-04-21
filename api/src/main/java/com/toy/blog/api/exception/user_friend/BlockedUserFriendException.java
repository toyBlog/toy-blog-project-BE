package com.toy.blog.api.exception.user_friend;

import com.toy.blog.api.exception.Error;

public class BlockedUserFriendException extends UserFriendException {

    public BlockedUserFriendException() {
        super(Error.BLOCKED_USER_FRIEND);
    }
}

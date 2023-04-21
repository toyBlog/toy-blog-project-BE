package com.toy.blog.api.exception.user_friend;

import com.toy.blog.api.exception.Error;

public class NotFoundUserFriend extends UserFriendException{

    public NotFoundUserFriend () {
        super(Error.NOT_FOUND_USER_FRIEND);
    }
}

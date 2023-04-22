package com.toy.blog.api.exception.liked;

import com.toy.blog.api.exception.Error;

public class NotFoundLikedException extends LikedException {

    public NotFoundLikedException() {
        super(Error.NOT_FOUND_LIKED);
    }
}

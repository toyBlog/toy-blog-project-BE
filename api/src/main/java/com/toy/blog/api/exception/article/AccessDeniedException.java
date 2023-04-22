package com.toy.blog.api.exception.article;

import com.toy.blog.api.exception.Error;

public class AccessDeniedException extends ArticleException {

    public AccessDeniedException() {
        super(Error.ACCESS_DENIED_EXCEPTION);
    }

}

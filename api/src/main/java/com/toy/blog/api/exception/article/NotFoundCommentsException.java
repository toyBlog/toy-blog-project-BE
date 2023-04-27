package com.toy.blog.api.exception.article;

import com.toy.blog.api.exception.Error;

public class NotFoundCommentsException extends ArticleException {

    public NotFoundCommentsException() {
        super(Error.NOT_FOUND_COMMENTS);
    }
}

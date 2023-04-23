package com.toy.blog.api.exception.article;

import com.toy.blog.api.exception.Error;

public class NotFoundArticleException extends ArticleException {

    public NotFoundArticleException() {
        super(Error.NOT_FOUND_ARTICLE);
    }

}

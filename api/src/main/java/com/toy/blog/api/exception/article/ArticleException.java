package com.toy.blog.api.exception.article;

import com.toy.blog.api.exception.Error;
import lombok.Getter;

public class ArticleException extends RuntimeException {

    @Getter
    final Error error;

    public ArticleException(Error error) {
        super(error.getMessage());
        this.error = error;
    }
}

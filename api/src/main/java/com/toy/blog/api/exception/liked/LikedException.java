package com.toy.blog.api.exception.liked;

import com.toy.blog.api.exception.Error;
import lombok.Getter;

public class LikedException extends RuntimeException {

    @Getter
    final Error error;

    public LikedException(Error error) {
        super(error.getMessage());
        this.error = error;
    }
}

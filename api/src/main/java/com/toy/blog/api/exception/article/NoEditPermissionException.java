package com.toy.blog.api.exception.article;

import com.toy.blog.api.exception.Error;

public class NoEditPermissionException extends ArticleException {

    public NoEditPermissionException() {
        super(Error.NO_EDIT_PERMISSION);
    }
}

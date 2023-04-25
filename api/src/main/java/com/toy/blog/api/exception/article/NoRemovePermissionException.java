package com.toy.blog.api.exception.article;

import com.toy.blog.api.exception.Error;

public class NoRemovePermissionException extends ArticleException{

    public NoRemovePermissionException() {
        super(Error.NO_REMOVE_PERMISSION);
    }
}

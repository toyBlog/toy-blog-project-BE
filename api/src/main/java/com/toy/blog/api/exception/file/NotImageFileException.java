package com.toy.blog.api.exception.file;

import com.toy.blog.api.exception.Error;

public class NotImageFileException extends FileException {
    public NotImageFileException() {
        super(Error.NOT_IMAGE_FILE);
    }
}

package com.toy.blog.api.exception.file;

import com.toy.blog.api.exception.Error;

public class FailSaveFileException extends FileException {

    public FailSaveFileException() {
        super(Error.FAIL_SAVE_FILE);
    }
}

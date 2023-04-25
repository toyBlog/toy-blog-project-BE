package com.toy.blog.api.exception.file;

import com.toy.blog.api.exception.Error;
import lombok.Getter;

public class FileException extends RuntimeException{

    @Getter
    final Error error;

    public FileException (Error error) {
        super(error.getMessage());
        this.error = error;
    }
}

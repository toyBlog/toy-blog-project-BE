package com.toy.blog.api.exception;

import com.toy.blog.api.exception.user.UserException;
import com.toy.blog.api.model.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = {"com.toy.blog.api.controller"})
public class CommonExceptionHandler {

    @ExceptionHandler(UserException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<Void> userExceptionHandler(UserException e) {

        return Response.<Void>builder()
                .code(e.getError().getCode())
                .message(e.getError().getMessage())
                .build();
    }
}

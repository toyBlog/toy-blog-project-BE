package com.toy.blog.api.exception;

import com.toy.blog.api.exception.user.UserException;
import com.toy.blog.api.exception.user_friend.UserFriendException;
import com.toy.blog.api.model.response.Response;
import com.toy.blog.auth.value.AuthError;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = {"com.toy.blog.api.controller"})
public class CommonExceptionHandler {

    @ExceptionHandler(UserException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<Void> userFriendExceptionHandler(UserException e) {

        return Response.<Void>builder()
                .code(e.getError().getCode())
                .message(e.getError().getMessage())
                .build();
    }

    @ExceptionHandler(UserFriendException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<Void> userFriendExceptionHandler(UserFriendException e) {

        return Response.<Void>builder()
                .code(e.getError().getCode())
                .message(e.getError().getMessage())
                .build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Response<Void> accessDeniedExceptionHandler(AccessDeniedException e) {
        return Response.<Void>builder()
                .code(AuthError.PERMISSION_DENIED.getCode())
                .message(AuthError.PERMISSION_DENIED.getMessage())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<Void> exceptionHandler(Exception e) {
        return Response.<Void>builder()
                .code(Error.INTERNAL_SERVER_ERROR.getCode())
                .message(Error.INTERNAL_SERVER_ERROR.getMessage())
                .build();
    }

}

package com.toy.blog.auth.exception.handler;

import com.toy.blog.auth.config.TokenProvider;
import com.toy.blog.auth.exception.UnauthorizedException;
import com.toy.blog.auth.exception.join.JoinException;
import com.toy.blog.auth.exception.login.ExpiredTokenException;
import com.toy.blog.auth.exception.login.InvalidTokenException;
import com.toy.blog.auth.exception.login.LoginException;
import com.toy.blog.auth.model.Response;
import com.toy.blog.auth.value.AuthError;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class AuthExceptionHandler {

    private final TokenProvider tokenProvider;

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public Response<Void> unauthorizedExceptionHandler(UnauthorizedException e) {
        return createResponse(AuthError.UNAUTHORIZED);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AccessDeniedException.class)
    public Response<Void> accessDeniedExceptionHandler(HttpServletRequest httpServletRequest, AccessDeniedException e) {
        log.warn("[data error]: message: {}", e.getMessage());
        try {
            String jwtToken = tokenProvider.getJwtToken(httpServletRequest);
            tokenProvider.isValidToken(jwtToken);
        } catch (ExpiredJwtException ex) {
            return createResponse(AuthError.EXPIRED_TOKEN);
        } catch (ExpiredTokenException | InvalidTokenException ex) {
            return createResponse(ex.getAuthError());
        } catch (Exception ignored) {
        }
        return createResponse(AuthError.UNAUTHORIZED);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidTokenException.class)
    public Response<Void> invalidTokenExceptionHandler(InvalidTokenException e) {
        log.warn("[auth error]: message: {}", e.getMessage());
        return createResponse(e.getAuthError());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(JoinException.class)
    public Response<Void> joinExceptionHandler(JoinException e) {
        log.error("message: {}", e.getMessage());

        AuthError authError = e.getAuthError();

        return Response.<Void>builder()
                .code(authError.getCode())
                .message("message: " + authError.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(LoginException.class)
    public Response<Void> loginExceptionHandler(LoginException e) {
        log.warn("message: {}", e.getMessage());

        AuthError authError = e.getAuthError();

        return Response.<Void>builder()
                .code(authError.getCode())
                .message("message: " + authError.getMessage())
                .build();
    }

    private Response<Void> createResponse(AuthError authError) {
        return createResponse(authError.getCode(), authError.getMessage());
    }

    private Response<Void> createResponse(Integer code, String message) {
        return Response.<Void>builder()
                .code(code)
                .message(message)
                .build();
    }


}

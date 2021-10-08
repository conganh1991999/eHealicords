package com.anhnc2.ehealicords.api;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.response.HttpResponse;
import com.anhnc2.ehealicords.data.response.HttpResponseImpl;
import com.anhnc2.ehealicords.exception.AppException;
import com.anhnc2.ehealicords.exception.AuthException;
import com.anhnc2.ehealicords.exception.WaitingChangePasswordException;
import com.anhnc2.ehealicords.exception.PasswordNotMatchException;
import com.anhnc2.ehealicords.exception.TheSameOldPasswordException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
Use this class to handle uncached exception in the controller
 */
@RestControllerAdvice
public class ApiExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public HttpResponse<Object> handleAuthException(AuthException exception) {
        LOGGER.info("Authentication failed", exception);
        return HttpResponseImpl.builder()
                .code(exception.getStatusCode())
                .message(exception.getMessage())
                .build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public HttpResponse<Object> handleAccessDeniedException(Exception exception) {
        LOGGER.info("AuthUser does not have access privileges", exception);
        return HttpResponseImpl.builder()
                .code(StatusCode.AUTHENTICATION_FAILED)
                .message("AuthUser does not have access privileges")
                .build();
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TheSameOldPasswordException.class)
    HttpResponse<Object> handleTheSamePassword() {
        return HttpResponseImpl.builder().code(StatusCode.THE_SAME_OLD_PASSWORD).build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PasswordNotMatchException.class)
    HttpResponse<Object> handlePasswordNotMatch() {
        return HttpResponseImpl.builder().code(StatusCode.PASSWORD_NOT_MATCH).build();
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(WaitingChangePasswordException.class)
    public HttpResponse<Object> raiseForceChangePassword(
            WaitingChangePasswordException exception) {

        return HttpResponseImpl.builder()
                .code(StatusCode.FORCE_CHANGE_PASSWORD)
                .build();
    }

    @ExceptionHandler(AppException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public HttpResponse<Object> handleException(AppException exception) {
        LOGGER.error("An uncatched exception occur when handling request", exception);
        return HttpResponseImpl.builder()
                .code(exception.getStatusCode())
                .message("Unexpected exception was occurred")
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public HttpResponse<Object> handleException(Exception exception) {
        LOGGER.error("An uncatched exception occur when handling request", exception);
        return HttpResponseImpl.builder()
                .code(StatusCode.INTERNAL_SERVER_ERROR)
                .message("Unexpected exception was occurred")
                .build();
    }
}

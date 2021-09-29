package com.anhnc2.ehealicords.exception;

import com.anhnc2.ehealicords.constant.StatusCode;

public class AuthException extends AppException {
    public AuthException(StatusCode statusCode, Exception cause) {
        super(statusCode, cause);
    }
}

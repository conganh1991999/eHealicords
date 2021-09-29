package com.anhnc2.ehealicords.exception;

import com.anhnc2.ehealicords.constant.StatusCode;
import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
    private final StatusCode statusCode;

    public AppException(StatusCode apiResponseCode) {
        super(apiResponseCode.name());
        this.statusCode = apiResponseCode;
    }

    public AppException(StatusCode statusCode, Exception cause) {
        super(statusCode.name(), cause);
        this.statusCode = statusCode;
    }
}

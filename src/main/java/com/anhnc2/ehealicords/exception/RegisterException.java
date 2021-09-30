package com.anhnc2.ehealicords.exception;

import com.anhnc2.ehealicords.constant.StatusCode;

public class RegisterException extends AppException {
    public RegisterException(StatusCode apiResponseCode) {
        super(apiResponseCode);
    }
}

package com.anhnc2.ehealicords.exception;

import com.anhnc2.ehealicords.constant.StatusCode;

public class LoginException extends AppException {
    public LoginException(StatusCode apiResponseCode) {
        super(apiResponseCode);
    }
}

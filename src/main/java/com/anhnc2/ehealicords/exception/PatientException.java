package com.anhnc2.ehealicords.exception;

import com.anhnc2.ehealicords.constant.StatusCode;

public class PatientException extends AppException {
    public PatientException(StatusCode apiResponseCode) {
        super(apiResponseCode);
    }
}

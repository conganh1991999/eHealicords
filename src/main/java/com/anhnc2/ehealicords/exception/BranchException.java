package com.anhnc2.ehealicords.exception;

import com.anhnc2.ehealicords.constant.StatusCode;
import lombok.Getter;

@Getter
public class BranchException extends AppException {
    public BranchException(StatusCode statusCode) {
        super(statusCode);
    }
}

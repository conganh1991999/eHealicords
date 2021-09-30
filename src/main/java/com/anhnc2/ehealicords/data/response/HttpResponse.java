package com.anhnc2.ehealicords.data.response;

import com.anhnc2.ehealicords.constant.StatusCode;

public interface HttpResponse<T> {
    StatusCode getCode();

    T getData();

    String getMessage();
}

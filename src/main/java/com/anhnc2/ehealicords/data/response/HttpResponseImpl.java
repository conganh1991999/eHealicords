package com.anhnc2.ehealicords.data.response;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
public class HttpResponseImpl<T> implements HttpResponse<T> {
    private final StatusCode code;
    private final String message;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private final T data;

    public static <T> HttpResponseImpl<T> success(T data) {
        return success(data, null);
    }

    public static <T> HttpResponseImpl<T> success(T data, String message) {
        return new HttpResponseImpl<>(StatusCode.SUCCESS, message, data);
    }
}

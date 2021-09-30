package com.anhnc2.ehealicords.data.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PasswordUpdateRequest {
    private String password;
    private String rePassword;
}

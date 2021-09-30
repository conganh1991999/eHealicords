package com.anhnc2.ehealicords.data.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PasscodeRegisterRequest {
    private String phoneNumber;
    private String verifyCode;
}

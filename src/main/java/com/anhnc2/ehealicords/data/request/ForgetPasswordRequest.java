package com.anhnc2.ehealicords.data.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ForgetPasswordRequest {
    @NotNull(message = "email must be not null")
    @Email(message = "email have a wrong format")
    String email;
}

package com.anhnc2.ehealicords.data.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class ForceChangePasswordRequest {

    @NotNull(message = "email must be a not null")
    @Email(message = "email wrong format")
    String email;

    @Size(min = 6, max = 20, message = "password must allow length 6 - 20")
    @NotNull(message = "password must be a not null")
    @NotBlank(message = "password must be not blank")
    String password;

    @NotNull(message = "old password must be a not null")
    @NotBlank(message = "old password must be not blank")
    String oldPassword;
}

package com.anhnc2.ehealicords.data.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class ForceChangePasswordRequest {

    @NotNull(message = "email must not be null")
    @Email(message = "email have a wrong format")
    String email;

    @Size(min = 6, max = 20, message = "new password must be 6 - 20 characters in length")
    @NotNull(message = "new password must not be null")
    @NotBlank(message = "new password must not be blank")
    String newPassword;

    @NotNull(message = "old password must not be null")
    @NotBlank(message = "old password must not be blank")
    String oldPassword;
}

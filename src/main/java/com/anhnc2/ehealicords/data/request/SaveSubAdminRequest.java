package com.anhnc2.ehealicords.data.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaveSubAdminRequest {
    @NotNull(message = "email must not be null")
    @Email(message = "email have a wrong format")
    String email;

    @NotNull(message = "full name must not be null")
    @NotBlank(message = "full name mus not be blank")
    String fullName;

    Integer branchId;
}

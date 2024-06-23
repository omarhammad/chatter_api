package com.example.chatter.presentation.api.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ValidateOtpRequestDto {

    @Size(min = 6, max = 6, message = "otp code must be exactly 6 digits")
    @NotNull(message = "otp code must be provided!")
    private String code;


    @NotNull(message = "email must be provided!")
    @Email(message = "email must be provided as example@email.com")
    private String email;

    public @Size(min = 6, max = 6) @NotNull String getCode() {
        return code;
    }

    public void setCode(@Size(min = 6, max = 6) @NotNull String code) {
        this.code = code;
    }

    public @NotNull(message = "email must be provided!") @Email(message = "email must be provided as example@email.com") String getEmail() {
        return email;
    }

    public void setEmail(@NotNull(message = "email must be provided!") @Email(message = "email must be provided as example@email.com") String email) {
        this.email = email;
    }
}

package com.example.chatter.presentation.api.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class GenerateOtpRequestDto {

    @NotNull(message = "email must be provided!")
    @Email(message = "email must be provided e.g example@mail.com")
    private String email;


    public @Email(message = "email must be provided e.g example@mail.com") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "email must be provided e.g example@mail.com") String email) {
        this.email = email;
    }
}

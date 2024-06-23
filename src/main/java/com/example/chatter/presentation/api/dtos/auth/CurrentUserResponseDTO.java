package com.example.chatter.presentation.api.dtos.auth;

import java.util.List;

public class CurrentUserResponseDTO {

    private String email;
    private List<String> userRoles;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<String> userRoles) {
        this.userRoles = userRoles;
    }
}

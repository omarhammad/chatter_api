package com.example.chatter.presentation.api.dtos.chatters;

import com.example.chatter.domain.ChatterStatus;

import java.time.LocalDateTime;

public class ChatterResponseDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private ChatterStatus status;

    private LocalDateTime lastSeen;

    private boolean isBlocked;


    public ChatterResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ChatterStatus getStatus() {
        return status;
    }

    public void setStatus(ChatterStatus status) {
        this.status = status;
    }

    public LocalDateTime getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(LocalDateTime lastSeen) {
        this.lastSeen = lastSeen;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }
}

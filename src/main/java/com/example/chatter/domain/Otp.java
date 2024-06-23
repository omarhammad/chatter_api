package com.example.chatter.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Otp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String email;

    private LocalDateTime timeToLive;


    private Boolean isUsed;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(LocalDateTime timeToLive) {
        this.timeToLive = timeToLive;
    }


    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "Otp{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", email='" + email + '\'' +
                ", timeToLive=" + timeToLive +
                ", isUsed=" + isUsed +
                '}';
    }
}

package com.example.chatter.services.contracts;

public interface IOtpService {

    void sendOtp(String email);

    boolean verifyOtp(String email, String userCode);
}

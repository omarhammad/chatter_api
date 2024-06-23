package com.example.chatter.services.contracts;

public interface IOtpService {

    String sendOtp(String email);

    boolean verifyOtp(String email, String userCode);
}

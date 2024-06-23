package com.example.chatter.technical.services.contracts;

import java.io.IOException;

public interface IMailService {

    void sendOtpEmail(String to, String otp) throws IOException;
}

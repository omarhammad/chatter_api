package com.example.chatter.util.exceptions;

public class OtpCodeExpiredException extends RuntimeException {
    public OtpCodeExpiredException(String s) {
        super(s);
    }
}

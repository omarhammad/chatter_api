package com.example.chatter.presentation.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {


    @GetMapping({"/", ""})
    public String loadAuthPage() {
        return "auth/auth";
    }


    @GetMapping("/verify")
    public String loadVerifyOtpPage() {
        return "auth/verify_otp";
    }

}

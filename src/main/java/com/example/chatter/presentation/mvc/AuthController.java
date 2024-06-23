package com.example.chatter.presentation.mvc;

import com.example.chatter.technical.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {


    @GetMapping({"/", ""})
    public String loadAuthPage(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails != null) {
            return "redirect:/chats";
        }
        return "auth/auth";
    }


    @GetMapping("/verify")
    public String loadVerifyOtpPage(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails != null) {
            return "redirect:/chats";
        }
        return "auth/verify_otp";
    }

}

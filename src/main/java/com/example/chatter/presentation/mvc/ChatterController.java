package com.example.chatter.presentation.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chatters")
public class ChatterController {


    @GetMapping("/complete-profile")
    public String loadCompleteProfilePage() {
        return "chatter/complete_profile";
    }


}

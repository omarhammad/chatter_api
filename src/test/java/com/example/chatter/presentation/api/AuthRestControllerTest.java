package com.example.chatter.presentation.api;

import com.example.chatter.domain.Chatter;
import com.example.chatter.presentation.api.dtos.auth.GenerateOtpRequestDto;
import com.example.chatter.presentation.api.dtos.auth.ValidateOtpRequestDto;
import com.example.chatter.technical.repo.ChattersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ChattersRepository chatterRepository;


    @Test
    public void sendAndVerifyOtpCodeSuccess() throws Exception {
        // Step 1: Send OTP
        GenerateOtpRequestDto sendOtpRequest = new GenerateOtpRequestDto();
        sendOtpRequest.setEmail("omarhammad767@gmail.com");

        MvcResult sendOtpResult = mockMvc.perform(post("/api/auth/send-otp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sendOtpRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        String sentOtpCode = sendOtpResult.getResponse().getContentAsString();
        System.out.println("Sent OTP Code: " + sentOtpCode);

        // Step 2: Verify OTP and capture session
        ValidateOtpRequestDto verifyOtpRequest = new ValidateOtpRequestDto();
        verifyOtpRequest.setEmail("omarhammad767@gmail.com");
        verifyOtpRequest.setCode(sentOtpCode);

        MvcResult verifyOtpResult = mockMvc.perform(post("/api/auth/verify-otp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(verifyOtpRequest)))
                .andExpect(status().isOk())
                .andReturn();
    }


    @Test
    public void sendOtpCodeWithWrongEmail() throws Exception {

        GenerateOtpRequestDto requestDto = new GenerateOtpRequestDto();
        requestDto.setEmail("asdasdasdsadasdasdas");

        mockMvc.perform(post("/api/auth/send-otp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").exists());
    }
}
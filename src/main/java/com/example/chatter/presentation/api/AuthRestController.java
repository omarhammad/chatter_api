package com.example.chatter.presentation.api;

import com.example.chatter.domain.Chatter;
import com.example.chatter.presentation.api.dtos.auth.CurrentUserResponseDTO;
import com.example.chatter.presentation.api.dtos.auth.GenerateOtpRequestDto;
import com.example.chatter.presentation.api.dtos.auth.ValidateOtpRequestDto;
import com.example.chatter.presentation.api.dtos.chatters.ChatterResponseDTO;
import com.example.chatter.services.contracts.IChattersService;
import com.example.chatter.services.contracts.IOtpService;
import com.example.chatter.technical.security.CustomUserDetails;
import com.example.chatter.util.exceptions.OtpCodeExpiredException;
import com.example.chatter.util.exceptions.OtpCodeNotSentException;
import com.example.chatter.util.exceptions.OtpInsertionException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    private final IOtpService otpService;
    private final IChattersService chattersService;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    private final Logger logger;

    public AuthRestController(IOtpService otpService, IChattersService chattersService, AuthenticationManager authenticationManager, ModelMapper modelMapper) {
        this.otpService = otpService;
        this.chattersService = chattersService;
        this.authenticationManager = authenticationManager;
        this.modelMapper = modelMapper;
        this.logger = LoggerFactory.getLogger(AuthRestController.class);
    }

    @PostMapping("/send-otp")
    public ResponseEntity<String> sentOtp(@RequestBody @Valid GenerateOtpRequestDto requestDto) {

        String otpCode;
        try {
            otpCode = otpService.sendOtp(requestDto.getEmail());

        } catch (OtpInsertionException | OtpCodeNotSentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send OTP. Please try again later.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(otpCode);

    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody ValidateOtpRequestDto requestDto, HttpServletRequest request) {
        try {
            boolean isVerified = otpService.verifyOtp(requestDto.getEmail(), requestDto.getCode());
            logger.info("Is verified : {}", isVerified);
            if (!isVerified) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (OtpCodeExpiredException e) {
            return ResponseEntity.status(HttpStatus.GONE).build();
        }

        String authType;
        Chatter chatter = chattersService.getChatterByEmailForAuth(requestDto.getEmail());
        if (chatter == null) {
            authType = "SIGN_UP";
            chattersService.createNewChatter(requestDto.getEmail());
        } else {
            authType = "SIGN_IN";
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getCode(), null);
        Authentication result = authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(result);

        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        return ResponseEntity.ok(authType);
    }


    @GetMapping("/current-user")
    public ResponseEntity<CurrentUserResponseDTO> getCurrentUser(@AuthenticationPrincipal CustomUserDetails userDetails) {

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        CurrentUserResponseDTO responseDTO = modelMapper.map(userDetails, CurrentUserResponseDTO.class);
        responseDTO.setEmail(userDetails.getUsername());
        List<String> userRoles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        responseDTO.setUserRoles(userRoles);
        return ResponseEntity.ok(responseDTO);

    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (existingValue, newValue) -> existingValue + ";" + newValue
                ));

        return ResponseEntity.badRequest().body(errors);
    }


}



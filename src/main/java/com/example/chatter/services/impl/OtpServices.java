package com.example.chatter.services.impl;

import com.example.chatter.domain.Otp;
import com.example.chatter.services.contracts.IOtpService;
import com.example.chatter.technical.repo.OtpRepository;
import com.example.chatter.technical.services.impl.MailService;
import com.example.chatter.util.exceptions.OtpCodeExpiredException;
import com.example.chatter.util.exceptions.OtpCodeNotSentException;
import com.example.chatter.util.exceptions.OtpInsertionException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class OtpServices implements IOtpService {

    private final OtpRepository otpRepository;
    private final MailService mailService;


    public OtpServices(OtpRepository otpRepository, MailService mailService) {
        this.otpRepository = otpRepository;
        this.mailService = mailService;
    }

    @Override
    @Transactional
    public String sendOtp(String email) {

        otpRepository.deleteAllByEmailAndIsUsedFalse(email);

        Otp otp = new Otp();
        otp.setEmail(email);
        otp.setCode(generateOTP());
        otp.setTimeToLive(LocalDateTime.now().plusMinutes(15));
        otp.setUsed(false);

        try {
            Otp savedOtp = otpRepository.save(otp);
            mailService.sendOtpEmail(email, savedOtp.getCode());
            return savedOtp.getCode();
        } catch (IOException e) {
            throw new OtpCodeNotSentException("Issue ending the otp code");
        } catch (Exception e) {
            throw new OtpInsertionException("Issue while generating new OTP");
        }

    }

    public String generateOTP() {
        return String.valueOf((int) (Math.random() * 900000) + 100000); // Generate a 6-digit OTP
    }

    @Override
    public boolean verifyOtp(String email, String userCode) {

        Otp otp = otpRepository.findByEmailAndIsUsedFalse(email).orElseThrow(() -> new EntityNotFoundException("OTP not Found!"));
        if (otp.getTimeToLive().isBefore(LocalDateTime.now())) {
            throw new OtpCodeExpiredException("Code Expired");
        }
        System.out.println("User code :" + userCode + " the generated code is :" + otp.getCode());

        if (!otp.getCode().equals(userCode)) {
            return false;
        }

        otp.setUsed(true);
        otpRepository.save(otp);
        return true;
    }

}


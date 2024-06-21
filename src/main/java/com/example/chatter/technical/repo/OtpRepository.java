package com.example.chatter.technical.repo;

import com.example.chatter.domain.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends JpaRepository<OTP, Long> {

}

package com.example.chatter.technical.repo;

import com.example.chatter.domain.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {

    void deleteAllByEmailAndIsUsedFalse(String email);

    Optional<Otp> findByEmailAndIsUsedFalse(String email);
}

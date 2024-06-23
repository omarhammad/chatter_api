package com.example.chatter.technical.repo;

import com.example.chatter.domain.Chatter;
import com.example.chatter.presentation.api.dtos.chatters.ChatterResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChattersRepository extends JpaRepository<Chatter, Long> {
    Optional<Chatter> findByEmail(String email);
}

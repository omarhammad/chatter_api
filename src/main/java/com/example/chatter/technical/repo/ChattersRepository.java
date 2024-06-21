package com.example.chatter.technical.repo;

import com.example.chatter.domain.Chatter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChattersRepository extends JpaRepository<Chatter, Long> {
}

package com.example.chatter.technical.repo;

import com.example.chatter.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatsRepository extends JpaRepository<Chat,Long> {
}

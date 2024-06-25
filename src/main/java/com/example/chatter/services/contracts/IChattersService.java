package com.example.chatter.services.contracts;

import com.example.chatter.domain.Chatter;
import com.example.chatter.presentation.api.dtos.chatters.ChatterResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IChattersService {

    ChatterResponseDTO createNewChatter(String email);

    Chatter getChatterByEmailForAuth(String email);

    void updateChatter(Long id, String email, MultipartFile profilePic, String bio, String username) throws IOException;

}

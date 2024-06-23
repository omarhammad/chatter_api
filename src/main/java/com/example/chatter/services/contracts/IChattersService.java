package com.example.chatter.services.contracts;

import com.example.chatter.domain.Chatter;
import com.example.chatter.presentation.api.dtos.chatters.ChatterResponseDTO;

public interface IChattersService {

    ChatterResponseDTO createNewChatter(String email);

    Chatter getChatterByEmailForAuth(String email);
}

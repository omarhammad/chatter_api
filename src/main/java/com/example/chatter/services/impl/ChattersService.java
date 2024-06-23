package com.example.chatter.services.impl;

import com.example.chatter.domain.Chatter;
import com.example.chatter.presentation.api.dtos.chatters.ChatterResponseDTO;
import com.example.chatter.services.contracts.IChattersService;
import com.example.chatter.technical.repo.ChattersRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ChattersService implements IChattersService {


    private final ChattersRepository chattersRepository;
    private final ModelMapper modelMapper;

    public ChattersService(ChattersRepository chattersRepository, ModelMapper modelMapper) {
        this.chattersRepository = chattersRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ChatterResponseDTO createNewChatter(String email) {

        Chatter chatter = new Chatter();
        chatter.setEmail(email);
        Chatter savedChatter = chattersRepository.save(chatter);
        return modelMapper.map(savedChatter, ChatterResponseDTO.class);
    }

    @Override
    public Chatter getChatterByEmailForAuth(String email) {
        return chattersRepository.findByEmail(email).orElse(null);
    }

    @Override
    public void updateChatter(Long id, String email, String firstName, String lastName, String username) {

        Chatter chatter = chattersRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Chatter Not Found!"));

        chatter.setEmail(email);
        chatter.setFirstName(firstName);
        chatter.setLastName(lastName);
        chatter.setUsername(username);

        chattersRepository.save(chatter);

    }

}

package com.example.chatter.services.impl;

import com.example.chatter.domain.Chatter;
import com.example.chatter.presentation.api.dtos.chatters.ChatterResponseDTO;
import com.example.chatter.services.contracts.IChattersService;
import com.example.chatter.technical.repo.ChattersRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class ChattersService implements IChattersService {


    private final ChattersRepository chattersRepository;
    private final ModelMapper modelMapper;

    @Value("${file.upload-dir}")
    private String uploadDir;

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
    public void updateChatter(Long id, String email, MultipartFile profilePic, String bio, String username) throws IOException {

        Chatter chatter = chattersRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Chatter Not Found!"));
        String profilePicDest = saveFile(profilePic, String.valueOf(id), "profile_pic");

        chatter.setId(id);
        chatter.setEmail(email);
        chatter.setUsername(username);
        chatter.setProfilePicUrl(profilePicDest);
        chatter.setBio(bio);
        chattersRepository.save(chatter);
    }

    private String saveFile(MultipartFile file, String userId, String filename) {
        if (file.isEmpty()) {
            throw new RuntimeException("Failed to store empty file.");
        }

        try {
            // Extract file extension
            String originalFilename = Objects.requireNonNull(file.getOriginalFilename());
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String filenameWithExtension = filename + fileExtension;

            // Create user-specific directory if it does not exist
            String userFolderName = "user_" + userId;
            Path userDir = Paths.get(uploadDir, userFolderName);
            if (!Files.exists(userDir)) {
                Files.createDirectories(userDir);
            }

            // Save the file
            Path path = Paths.get(userDir.toString(), filenameWithExtension);
            Files.write(path, file.getBytes());

            // Return the relative path
            return uploadDir + "/" + Paths.get(userFolderName, filenameWithExtension).toString().replace("\\", "/");
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file.", e);
        }
    }


}

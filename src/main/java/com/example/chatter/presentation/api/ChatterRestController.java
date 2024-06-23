package com.example.chatter.presentation.api;

import com.example.chatter.presentation.api.dtos.chatters.UpdateChatterRequestDTO;
import com.example.chatter.services.contracts.IChattersService;
import com.example.chatter.technical.security.CustomUserDetails;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chatters")
public class ChatterRestController {

    private final IChattersService chattersService;

    public ChatterRestController(IChattersService chattersService) {
        this.chattersService = chattersService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateChatterInfo(@RequestBody @Valid UpdateChatterRequestDTO requestDTO,
                                                  @PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (!userDetails.getId().equals(id)) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (!id.equals(requestDTO.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        try {
            chattersService.updateChatter(requestDTO.getId(), requestDTO.getEmail(), requestDTO.getFirstName()
                    , requestDTO.getLastName(), requestDTO.getUsername());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.noContent().build();
    }

}

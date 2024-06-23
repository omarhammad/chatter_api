package com.example.chatter.technical.security;

import com.example.chatter.domain.Chatter;
import com.example.chatter.services.contracts.IChattersService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final IChattersService chattersService;

    public CustomUserDetailsService(IChattersService chattersService) {
        this.chattersService = chattersService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Chatter chatter = chattersService.getChatterByEmailForAuth(username);
        if (chatter == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Assuming all users have ROLE_USER authority for simplicity
        return new CustomUserDetails(chatter.getId(), chatter.getEmail(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}

package com.example.chatter.presentation.api.dtos.chatters;

import org.springframework.web.multipart.MultipartFile;

public class UpdateChatterRequestDTO {

    private Long id;

    private MultipartFile profilePic;

    private String bio;

    private String username;

    private String email;


    public UpdateChatterRequestDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public MultipartFile getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(MultipartFile profilePic) {
        this.profilePic = profilePic;
    }
}

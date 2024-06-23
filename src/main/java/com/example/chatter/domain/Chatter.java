package com.example.chatter.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Chatter {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;

    private ChatterStatus status;

    private LocalDateTime lastSeen;

    private boolean isBlocked;

    @ManyToMany
    @JoinTable(
            name = "chatter_chat",
            joinColumns = @JoinColumn(name = "chatter_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id")
    )
    private Set<Chat> chats;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public void setEmail(String phoneNumber) {
        this.email = phoneNumber;
    }

    public LocalDateTime getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(LocalDateTime lastSeen) {
        this.lastSeen = lastSeen;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public Set<Chat> getChats() {
        return chats;
    }

    public void setChats(Set<Chat> chats) {
        this.chats = chats;
    }

    public ChatterStatus getStatus() {
        return status;
    }

    public void setStatus(ChatterStatus status) {
        this.status = status;
    }
}

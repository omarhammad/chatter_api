package com.example.chatter.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private ContentType contentType;

    @ManyToOne
    private Chat chat;

    private LocalDateTime sentTime;

    @ManyToOne
    private Chatter sender;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }


    public LocalDateTime getSentTime() {
        return sentTime;
    }

    public void setSentTime(LocalDateTime sentTime) {
        this.sentTime = sentTime;
    }

    public Chatter getSender() {
        return sender;
    }

    public void setSender(Chatter sender) {
        this.sender = sender;
    }
}

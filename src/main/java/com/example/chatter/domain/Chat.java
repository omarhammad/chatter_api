package com.example.chatter.domain;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "chats")
    private Set<Chatter> chatters;

    @OneToMany(mappedBy = "chat")
    private Set<Message> messages;

    @OneToOne
    private Message lastMessage;
    

    public void setId(Long id) {
        this.id = id;

    }

    public Long getId() {
        return id;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Set<Chatter> getChatters() {
        return chatters;
    }

    public void setChatters(Set<Chatter> chatters) {
        this.chatters = chatters;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

}

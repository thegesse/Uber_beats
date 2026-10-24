package com.thegeese.backend.model.Chatting;

import com.thegeese.backend.model.User;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class Message {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Conversation conversation;

    @ManyToOne(optional = false)
    private User sender;

    @Column(nullable = false, length = 5000)
    private String content;

    @Column(nullable = false)
    private Instant sentAt;
}

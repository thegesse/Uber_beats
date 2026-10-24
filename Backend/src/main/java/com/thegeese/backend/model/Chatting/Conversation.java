package com.thegeese.backend.model.Chatting;

import com.thegeese.backend.model.User;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Conversation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User buyer;

    @ManyToOne(optional = false)
    private User seller;

    @OneToMany(mappedBy = "conversation")
    private List<Message> messages = new ArrayList<>();
}

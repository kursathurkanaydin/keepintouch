package com.hk.whatsapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
public abstract class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private LocalDateTime creationDate;

    private String name;

    @ManyToMany(mappedBy = "chats")
    private Set<User> participants;

    @OneToMany
    @OrderColumn(name = "order_index")
    private List<Message> messages;

    @PrePersist
    protected void onCreate() {
        creationDate = LocalDateTime.now();
    }

}

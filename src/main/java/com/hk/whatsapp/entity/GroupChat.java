package com.hk.whatsapp.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;
@Getter
@Setter
@Entity
public class GroupChat extends Chat {

    @Column(name = "creator_mail")
    private String creatorMail;

    @ManyToMany
    @JoinTable(
            name = "group_chat_users",
            joinColumns = @JoinColumn(name = "group_chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> members;
}

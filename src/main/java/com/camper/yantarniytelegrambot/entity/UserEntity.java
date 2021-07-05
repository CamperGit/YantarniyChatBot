package com.camper.yantarniytelegrambot.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Setter
@EqualsAndHashCode
@Table(name = "users")
public class UserEntity {
    private Integer userId;
    private String chatId;
    private String username;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    public Integer getUserId() {
        return userId;
    }

    @Basic
    @Column(name = "chat_id", nullable = false, length = 150)
    public String getChatId() {
        return chatId;
    }

    @Basic
    @Column(name = "username", nullable = false, length = 150)
    public String getUsername() {
        return username;
    }

    public UserEntity(String chatId, String username) {
        this.chatId = chatId;
        this.username = username;
    }
}

package com.camper.yantarniytelegrambot.entity;

import com.camper.yantarniytelegrambot.enums.UserRole;
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
    private String firstname;
    private String lastname;
    private String username;
    private String number;
    private UserRole role;

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
    @Column(name = "firstname", nullable = false, length = 150)
    public String getFirstname() {
        return firstname;
    }

    @Basic
    @Column(name = "lastname", length = 150)
    public String getLastname() {
        return lastname;
    }

    @Basic
    @Column(name = "username", length = 150)
    public String getUsername() {
        return username;
    }

    @Basic
    @Column(name = "number", length = 15)
    public String getNumber() {
        return number;
    }

    @Basic
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    public UserRole getRole() {
        return role;
    }

    public UserEntity(String chatId, String firstname, String lastname, String username, String number, UserRole role) {
        this.chatId = chatId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.number = number;
        this.role = role;
    }
}

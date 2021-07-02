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
@Table(name = "card_types")
public class CardType {
    private Integer cardId;
    private String title;
    private String description;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id", nullable = false)
    public Integer getCardId() {
        return cardId;
    }

    @Basic
    @Column(name = "title", nullable = false, length = 80)
    public String getTitle() {
        return title;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 500)
    public String getDescription() {
        return description;
    }
}

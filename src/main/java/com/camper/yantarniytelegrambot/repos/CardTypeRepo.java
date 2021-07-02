package com.camper.yantarniytelegrambot.repos;

import com.camper.yantarniytelegrambot.entity.CardType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardTypeRepo extends JpaRepository<CardType, Integer> {
}

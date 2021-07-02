package com.camper.yantarniytelegrambot.repos;

import com.camper.yantarniytelegrambot.entity.CardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardTypeRepo extends JpaRepository<CardType, Integer> {
}

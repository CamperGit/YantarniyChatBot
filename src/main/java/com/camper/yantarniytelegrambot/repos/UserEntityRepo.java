package com.camper.yantarniytelegrambot.repos;

import com.camper.yantarniytelegrambot.entity.UserEntity;
import com.camper.yantarniytelegrambot.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityRepo extends JpaRepository<UserEntity,Integer> {
    UserEntity findByChatId(String chatId);
}

package com.camper.yantarniytelegrambot.repos;

import com.camper.yantarniytelegrambot.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityRepo extends JpaRepository<UserEntity,Integer> {
}

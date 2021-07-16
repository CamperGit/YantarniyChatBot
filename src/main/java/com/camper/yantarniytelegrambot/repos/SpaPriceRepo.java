package com.camper.yantarniytelegrambot.repos;

import com.camper.yantarniytelegrambot.entity.SpaPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpaPriceRepo extends JpaRepository<SpaPrice, Integer> {
}

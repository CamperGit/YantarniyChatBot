package com.camper.yantarniytelegrambot.repos;

import com.camper.yantarniytelegrambot.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepo extends JpaRepository<Sale,Integer> {
}
package com.camper.yantarniytelegrambot.repos;

import com.camper.yantarniytelegrambot.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepo extends JpaRepository<Location,Integer> {
    Location findLocationByTitle(String title);
}

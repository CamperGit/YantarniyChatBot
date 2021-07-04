package com.camper.yantarniytelegrambot.repos;

import com.camper.yantarniytelegrambot.entity.Schedule;
import com.camper.yantarniytelegrambot.enums.ScheduleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepo extends JpaRepository<Schedule,Integer> {
    Schedule findByType(ScheduleType type);
    List<Schedule> findAllByType(ScheduleType type);
}

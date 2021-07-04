package com.camper.yantarniytelegrambot.services;

import com.camper.yantarniytelegrambot.entity.Location;
import com.camper.yantarniytelegrambot.entity.Schedule;
import com.camper.yantarniytelegrambot.enums.ScheduleType;
import com.camper.yantarniytelegrambot.repos.ScheduleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ScheduleService {
    private ScheduleRepo scheduleRepo;

    @Transactional(readOnly = true)
    public Schedule findByType(ScheduleType type) {
        return scheduleRepo.findByType(type);
    }

    @Transactional(readOnly = true)
    public List<Schedule> findScheduleChanges() {
        return scheduleRepo.findAllByType(ScheduleType.CHANGES);
    }

    /**
     * retrieve an entity from the database if it exists or else put schedule in database
     * @param schedule an example entity to retrieve from the database or a candidate to add to the database
     * @return schedule from database
     */
    public Schedule putIfAbsent(Schedule schedule) {
        return scheduleRepo.findOne(Example.of(schedule)).orElseGet(()-> scheduleRepo.save(schedule));
    }

    @Autowired
    public void setScheduleRepo(ScheduleRepo scheduleRepo) {
        this.scheduleRepo = scheduleRepo;
    }
}

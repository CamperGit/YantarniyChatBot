package com.camper.yantarniytelegrambot.services;

import com.camper.yantarniytelegrambot.entity.Location;
import com.camper.yantarniytelegrambot.entity.Sale;
import com.camper.yantarniytelegrambot.repos.LocationRepo;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LocationService {
    private LocationRepo locationRepo;

    /**
     * retrieve an entity from the database if it exists or else put location in database
     * @param location an example entity to retrieve from the database or a candidate to add to the database
     * @return location from database
     */
    public Location putIfAbsent(Location location) {
        return locationRepo.findOne(Example.of(location)).orElseGet(()-> locationRepo.save(location));
    }

    @Autowired
    public void setLocationRepo(LocationRepo locationRepo) {
        this.locationRepo = locationRepo;
    }
}

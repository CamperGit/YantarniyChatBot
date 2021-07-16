package com.camper.yantarniytelegrambot.services;

import com.camper.yantarniytelegrambot.repos.SpaServiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class SpaService {
    private SpaServiceRepo spaServiceRepo;

    

    @Autowired
    public void setSpaServiceRepo(SpaServiceRepo spaServiceRepo) {
        this.spaServiceRepo = spaServiceRepo;
    }
}

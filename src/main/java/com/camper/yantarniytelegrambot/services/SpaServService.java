package com.camper.yantarniytelegrambot.services;

import com.camper.yantarniytelegrambot.entity.SpaService;
import com.camper.yantarniytelegrambot.repos.SpaServiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class SpaServService {
    private SpaServiceRepo spaServiceRepo;

    @Transactional(readOnly = true)
    public List<SpaService> findAll() {
        return spaServiceRepo.findAll();
    }

    @Autowired
    public void setSpaServiceRepo(SpaServiceRepo spaServiceRepo) {
        this.spaServiceRepo = spaServiceRepo;
    }
}

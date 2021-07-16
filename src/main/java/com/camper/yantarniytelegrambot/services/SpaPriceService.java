package com.camper.yantarniytelegrambot.services;

import com.camper.yantarniytelegrambot.entity.Sale;
import com.camper.yantarniytelegrambot.entity.SpaPrice;
import com.camper.yantarniytelegrambot.repos.SpaPriceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class SpaPriceService {
    private SpaPriceRepo spaPriceRepo;

    @Transactional(readOnly = true)
    public List<SpaPrice> findAll() {
        return spaPriceRepo.findAll();
    }

    public SpaPrice putIfAbsent(SpaPrice price) {
        return spaPriceRepo.findOne(Example.of(price)).orElseGet(()-> spaPriceRepo.save(price));
    }

    @Autowired
    public void setSpaServiceRepo(SpaPriceRepo spaPriceRepo) {
        this.spaPriceRepo = spaPriceRepo;
    }
}

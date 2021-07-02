package com.camper.yantarniytelegrambot.services;

import com.camper.yantarniytelegrambot.entity.CardType;
import com.camper.yantarniytelegrambot.repos.CardTypeRepo;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CardTypeService {
    private CardTypeRepo cardTypeRepo;

    @Transactional(readOnly = true)
    public List<CardType> findAll() {
        return cardTypeRepo.findAll();
    }

    @Setter
    public void setCardTypeRepo(CardTypeRepo cardTypeRepo) {
        this.cardTypeRepo = cardTypeRepo;
    }
}

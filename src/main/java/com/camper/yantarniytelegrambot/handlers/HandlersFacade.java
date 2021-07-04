package com.camper.yantarniytelegrambot.handlers;

import com.camper.yantarniytelegrambot.handlers.ClubCards.ClubCardButtonHandler;
import com.camper.yantarniytelegrambot.handlers.ClubCards.ClubCardSalesButtonHandler;
import com.camper.yantarniytelegrambot.handlers.ClubCards.ClubCardTypeButtonHandler;
import com.camper.yantarniytelegrambot.handlers.Fitness.FitnessButtonHandler;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Service
public class HandlersFacade {
    private final ClubCardButtonHandler clubCardButtonHandler;
    private final ClubCardTypeButtonHandler clubCardTypeButtonHandler;
    private final ClubCardSalesButtonHandler clubCardSalesButtonHandler;
    private final FitnessButtonHandler fitnessButtonHandler;

    @Autowired
    public HandlersFacade(ClubCardButtonHandler clubCardButtonHandler, ClubCardTypeButtonHandler clubCardTypeButtonHandler,
                          ClubCardSalesButtonHandler clubCardSalesButtonHandler, FitnessButtonHandler fitnessButtonHandler) {
        this.clubCardButtonHandler = clubCardButtonHandler;
        this.clubCardTypeButtonHandler = clubCardTypeButtonHandler;
        this.clubCardSalesButtonHandler = clubCardSalesButtonHandler;
        this.fitnessButtonHandler = fitnessButtonHandler;
    }
}
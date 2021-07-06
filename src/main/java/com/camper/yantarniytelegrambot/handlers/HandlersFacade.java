package com.camper.yantarniytelegrambot.handlers;

import com.camper.yantarniytelegrambot.handlers.ClubCards.CCSalesContactUsButtonHandler;
import com.camper.yantarniytelegrambot.handlers.ClubCards.ClubCardButtonHandler;
import com.camper.yantarniytelegrambot.handlers.ClubCards.ClubCardSalesButtonHandler;
import com.camper.yantarniytelegrambot.handlers.ClubCards.ClubCardTypeButtonHandler;
import com.camper.yantarniytelegrambot.handlers.Fitness.FitnessButtonHandler;
import com.camper.yantarniytelegrambot.handlers.Spa.SpaButtonHandler;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Service
public class HandlersFacade {
    private final ClubCardButtonHandler clubCardButtonHandler;
    private final ClubCardTypeButtonHandler clubCardTypeButtonHandler;
    private final ClubCardSalesButtonHandler clubCardSalesButtonHandler;
    private final CCSalesContactUsButtonHandler ccSalesContactUsButtonHandler;
    private final FitnessButtonHandler fitnessButtonHandler;
    private final SpaButtonHandler spaButtonHandler;
    private final CallManagerButtonHandler callManagerButtonHandler;

    @Autowired
    public HandlersFacade(ClubCardButtonHandler clubCardButtonHandler, ClubCardTypeButtonHandler clubCardTypeButtonHandler,
                          ClubCardSalesButtonHandler clubCardSalesButtonHandler, CCSalesContactUsButtonHandler ccSalesContactUsButtonHandler,
                          FitnessButtonHandler fitnessButtonHandler, SpaButtonHandler spaButtonHandler, CallManagerButtonHandler callManagerButtonHandler) {
        this.clubCardButtonHandler = clubCardButtonHandler;
        this.clubCardTypeButtonHandler = clubCardTypeButtonHandler;
        this.clubCardSalesButtonHandler = clubCardSalesButtonHandler;
        this.ccSalesContactUsButtonHandler = ccSalesContactUsButtonHandler;
        this.fitnessButtonHandler = fitnessButtonHandler;
        this.spaButtonHandler = spaButtonHandler;
        this.callManagerButtonHandler = callManagerButtonHandler;
    }
}

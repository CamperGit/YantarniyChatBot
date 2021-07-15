package com.camper.yantarniytelegrambot.handlers;

import com.camper.yantarniytelegrambot.handlers.Sales.CCSalesContactUsButtonHandler;
import com.camper.yantarniytelegrambot.handlers.ClubCards.ClubCardButtonHandler;
import com.camper.yantarniytelegrambot.handlers.Sales.ClubCardSalesButtonHandler;
import com.camper.yantarniytelegrambot.handlers.ClubCards.ClubCardTypeButtonHandler;
import com.camper.yantarniytelegrambot.handlers.Fitness.FitnessButtonHandler;
import com.camper.yantarniytelegrambot.handlers.Sales.SalesMenuButtonHandler;
import com.camper.yantarniytelegrambot.handlers.Schedule.ScheduleMenuButtonHandler;
import com.camper.yantarniytelegrambot.handlers.Spa.SpaButtonHandler;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Service
public class HandlersFacade {
    private final SalesMenuButtonHandler salesMenuButtonHandler;
    private final ClubCardButtonHandler clubCardButtonHandler;
    private final ClubCardTypeButtonHandler clubCardTypeButtonHandler;
    private final FitnessButtonHandler fitnessButtonHandler;
    private final ScheduleMenuButtonHandler scheduleMenuButtonHandler;
    private final SpaButtonHandler spaButtonHandler;
    private final CallManagerButtonHandler callManagerButtonHandler;
    private final SberQRButtonHandler sberQRButtonHandler;

    @Autowired

    public HandlersFacade(SalesMenuButtonHandler salesMenuButtonHandler, ClubCardButtonHandler clubCardButtonHandler,
                          ClubCardTypeButtonHandler clubCardTypeButtonHandler, FitnessButtonHandler fitnessButtonHandler,
                          ScheduleMenuButtonHandler scheduleMenuButtonHandler, SpaButtonHandler spaButtonHandler,
                          CallManagerButtonHandler callManagerButtonHandler, SberQRButtonHandler sberQRButtonHandler) {
        this.salesMenuButtonHandler = salesMenuButtonHandler;
        this.clubCardButtonHandler = clubCardButtonHandler;
        this.clubCardTypeButtonHandler = clubCardTypeButtonHandler;
        this.fitnessButtonHandler = fitnessButtonHandler;
        this.scheduleMenuButtonHandler = scheduleMenuButtonHandler;
        this.spaButtonHandler = spaButtonHandler;
        this.callManagerButtonHandler = callManagerButtonHandler;
        this.sberQRButtonHandler = sberQRButtonHandler;
    }
}

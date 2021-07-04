package com.camper.yantarniytelegrambot.handlers;

import com.camper.yantarniytelegrambot.handlers.ClubCards.ClubCardButtonHandler;
import com.camper.yantarniytelegrambot.handlers.ClubCards.ClubCardSalesButtonHandler;
import com.camper.yantarniytelegrambot.handlers.ClubCards.ClubCardTypeButtonHandler;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Service
public class HandlersFacade {
    private final ClubCardButtonHandler clubCardButtonHandler;
    private final ClubCardTypeButtonHandler clubCardTypeButtonHandler;
    private final ClubCardSalesButtonHandler clubCardSalesButtonHandler;

    @Autowired
    public HandlersFacade(ClubCardButtonHandler clubCardButtonHandler, ClubCardTypeButtonHandler clubCardTypeButtonHandler, ClubCardSalesButtonHandler clubCardSalesButtonHandler) {
        this.clubCardButtonHandler = clubCardButtonHandler;
        this.clubCardTypeButtonHandler = clubCardTypeButtonHandler;
        this.clubCardSalesButtonHandler = clubCardSalesButtonHandler;
    }
}

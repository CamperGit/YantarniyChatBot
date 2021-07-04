package com.camper.yantarniytelegrambot.handlers;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Service
public class HandlersFacade {
    private final ClubCardButtonHandler clubCardButtonHandler;
    private final ClubCardTypeButtonHandler clubCardTypeButtonHandler;

    @Autowired
    public HandlersFacade(ClubCardButtonHandler clubCardButtonHandler, ClubCardTypeButtonHandler clubCardTypeButtonHandler) {
        this.clubCardButtonHandler = clubCardButtonHandler;
        this.clubCardTypeButtonHandler = clubCardTypeButtonHandler;
    }
}

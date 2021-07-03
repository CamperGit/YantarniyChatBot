package com.camper.yantarniytelegrambot.handlers;


import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Component
public class BotActionListener {

    public List<BotApiMethod<?>> handleClubCartButton(String chatId) {
        return new ClubCardButtonHandler().handle(chatId);
    }
}

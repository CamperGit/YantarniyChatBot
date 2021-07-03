package com.camper.yantarniytelegrambot.handlers;


import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

@Component
public class BotActionListener {

    public SendMessage handleClubCartButton(String chatId) {
        return new SendMessage(chatId,"text");
    }
}

package com.camper.yantarniytelegrambot.handlers;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;

public class ClubCardButtonHandler implements BotButtonHandler{
    @Override
    public List<BotApiMethod<?>> handle(String chatId) {
        List<BotApiMethod<?>> answers = new ArrayList<>();
        SendMessage sendMessage = new SendMessage(chatId,"clubCardText");
        answers.add(sendMessage);
        return answers;
    }
}

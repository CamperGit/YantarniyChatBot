package com.camper.yantarniytelegrambot.handlers;

import com.camper.yantarniytelegrambot.services.LocaleMessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;

public class ClubCardButtonHandler implements BotButtonHandler{
    private LocaleMessageSource localeMessageSource;

    @Override
    public List<BotApiMethod<?>> handle(String chatId) {
        List<BotApiMethod<?>> answers = new ArrayList<>();
        SendMessage sendMessage = new SendMessage(chatId, localeMessageSource.getMessage("onAction.clubCartsButton"));
        answers.add(sendMessage);
        return answers;
    }

    public ClubCardButtonHandler(LocaleMessageSource localeMessageSource) {
        this.localeMessageSource = localeMessageSource;
    }
}

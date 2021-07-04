package com.camper.yantarniytelegrambot.handlers;

import com.camper.yantarniytelegrambot.services.LocaleMessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;

@Component
public class ClubCardTypeButtonHandler implements BotButtonHandler {

    private LocaleMessageSource localeMessageSource;

    @Override
    public List<BotApiMethod<?>> handle(String chatId, CallbackQuery query) {
        return null;
    }

    @Autowired
    public void setLocaleMessageSource(LocaleMessageSource localeMessageSource) {
        this.localeMessageSource = localeMessageSource;
    }
}

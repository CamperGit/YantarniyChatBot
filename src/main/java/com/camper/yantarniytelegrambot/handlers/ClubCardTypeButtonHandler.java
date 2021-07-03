package com.camper.yantarniytelegrambot.handlers;

import com.camper.yantarniytelegrambot.services.LocaleMessageSource;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;

public class ClubCardTypeButtonHandler implements BotButtonHandler {

    private LocaleMessageSource localeMessageSource;

    @Override
    public List<BotApiMethod<?>> handle(String chatId, CallbackQuery query) {
        return null;
    }

    public ClubCardTypeButtonHandler(LocaleMessageSource localeMessageSource) {
        this.localeMessageSource = localeMessageSource;
    }
}

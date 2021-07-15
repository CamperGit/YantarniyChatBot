package com.camper.yantarniytelegrambot.handlers.Fitness.Coaches;

import com.camper.yantarniytelegrambot.handlers.BotButtonHandler;
import com.camper.yantarniytelegrambot.services.LocaleMessageSource;
import com.camper.yantarniytelegrambot.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class FitnessContactUsButtonHandler implements BotButtonHandler {
    private LocaleMessageSource localeMessageSource;

    @Override
    public List<PartialBotApiMethod<?>> handle(String chatId, CallbackQuery query) {
        return new ArrayList<>(Utils.changeMessage(localeMessageSource.getMessage("other.contactUsFitness"),
                chatId,
                query.getMessage(),
                BotButtonHandler.getReturnMarkup("handleFitnessButton", true)));
    }

    @Autowired
    public void setLocaleMessageSource(LocaleMessageSource localeMessageSource) {
        this.localeMessageSource = localeMessageSource;
    }
}

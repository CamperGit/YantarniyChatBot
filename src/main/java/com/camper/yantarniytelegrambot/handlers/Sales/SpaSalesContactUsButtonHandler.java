package com.camper.yantarniytelegrambot.handlers.Sales;

import com.camper.yantarniytelegrambot.handlers.BotButtonHandler;
import com.camper.yantarniytelegrambot.services.LocaleMessageSource;
import com.camper.yantarniytelegrambot.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.ArrayList;
import java.util.List;

@Component
public class SpaSalesContactUsButtonHandler implements BotButtonHandler {
    private LocaleMessageSource localeMessageSource;

    @Override
    public List<PartialBotApiMethod<?>> handle(String chatId, CallbackQuery query) {
        return new ArrayList<>(Utils.changeMessage(localeMessageSource.getMessage("other.contactUsSpaSale"),
                chatId,
                query.getMessage(),
                BotButtonHandler.getReturnMarkup("handleSpaSalesButton", true)));
    }

    @Autowired
    public void setLocaleMessageSource(LocaleMessageSource localeMessageSource) {
        this.localeMessageSource = localeMessageSource;
    }
}

package com.camper.yantarniytelegrambot.handlers;


import com.camper.yantarniytelegrambot.services.LocaleMessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Service
public class BotActionListener {
    private LocaleMessageSource localeMessageSource;

    public List<BotApiMethod<?>> handleClubCartButton(String chatId) {
        return new ClubCardButtonHandler(localeMessageSource).handle(chatId);
    }

    @Autowired
    public void setLocaleMessageSource(LocaleMessageSource localeMessageSource) {
        this.localeMessageSource = localeMessageSource;
    }
}

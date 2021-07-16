package com.camper.yantarniytelegrambot.handlers.SpaService.Services;

import com.camper.yantarniytelegrambot.handlers.BotButtonHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;

@Component
public class NailsSSButtonHandler implements BotButtonHandler {
    @Override
    public List<PartialBotApiMethod<?>> handle(String chatId, CallbackQuery query) {
        return null;
    }
}

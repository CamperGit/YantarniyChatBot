package com.camper.yantarniytelegrambot.handlers;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;

public interface BotButtonHandler {
    List<BotApiMethod<?>> handle(String chatId, CallbackQuery query);
}

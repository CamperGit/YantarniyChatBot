package com.camper.yantarniytelegrambot.handlers;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.util.List;

public interface BotButtonHandler {
    List<BotApiMethod<?>> handle(String chatId);
}

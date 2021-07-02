package com.camper.yantarniytelegrambot.botapi;

import com.camper.yantarniytelegrambot.entity.CardType;
import com.camper.yantarniytelegrambot.services.CardTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.jws.Oneway;
import java.util.List;

public class YantarniyTelegramBot extends TelegramWebhookBot {
    private final String WEB_HOOK_PATH;
    private final String USERNAME;
    private final String TOKEN;
    private CardTypeService cardTypeService;

    public YantarniyTelegramBot(DefaultBotOptions options,String WEB_HOOK_PATH, String USERNAME, String TOKEN) {
        super(options);
        this.WEB_HOOK_PATH = WEB_HOOK_PATH;
        this.USERNAME = USERNAME;
        this.TOKEN = TOKEN;
    }

    @Override
    public String getBotUsername() {
        return USERNAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    @Override
    public String getBotPath() {
        return WEB_HOOK_PATH;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        if (update.getMessage() != null && update.getMessage().hasText()) {
            String chatId = update.getMessage().getChatId().toString();
            try {
                execute(new SendMessage(chatId,"Hi " + update.getMessage().getText()));
                List<CardType> cards = cardTypeService.findAll();
                for (CardType card : cards) {
                    execute(new SendMessage(chatId,card.getTitle()));
                }
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Autowired
    public void setCardTypeService(CardTypeService cardTypeService) {
        this.cardTypeService = cardTypeService;
    }
}
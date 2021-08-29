package com.camper.yantarniytelegrambot.controller;

import com.camper.yantarniytelegrambot.botapi.YantarniyTelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
@Slf4j
public class WebHookController {
    private YantarniyTelegramBot telegramBot;

//    @PostMapping("/")
//    public BotApiMethod<?> onUpdateReceiver(@RequestBody Update update) {
//        return telegramBot.onWebhookUpdateReceived(update);
//    }

    @GetMapping
    public void kaffeineUpdate() {
        log.info("Kaffein ping task");
    }

    @Autowired
    public void setTelegramBot(YantarniyTelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }
}

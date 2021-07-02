package com.camper.yantarniytelegrambot.appconfig;

import com.camper.yantarniytelegrambot.YantarniyTelegramBot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiConstants;
import org.telegram.telegrambots.meta.TelegramBotsApi;

@Configuration
@ConfigurationProperties("telegramBot")
public class BotConfig {

    @Bean
    public YantarniyTelegramBot telegramBot(String webHookPath, String username, String token) {
        return new YantarniyTelegramBot(new DefaultBotOptions(),webHookPath,username,token);
    }
}

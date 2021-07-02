package com.camper.yantarniytelegrambot.appconfig;

import com.camper.yantarniytelegrambot.YantarniyTelegramBot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiConstants;
import org.telegram.telegrambots.meta.TelegramBotsApi;

@Configuration
@PropertySource("classpath:bot.properties")
@ComponentScan
public class BotConfig {
    @Bean
    public YantarniyTelegramBot telegramBot(@Value("${telegrambot.webHookPath}")String webHookPath,
                                            @Value("${telegrambot.username}") String username,
                                            @Value("${telegrambot.token}") String token) {
        return new YantarniyTelegramBot(new DefaultBotOptions(),webHookPath,username,token);
    }
}

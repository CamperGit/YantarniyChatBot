package com.camper.yantarniytelegrambot.appconfig;

import com.camper.yantarniytelegrambot.botapi.YantarniyTelegramBot;
import com.camper.yantarniytelegrambot.services.LocaleMessageSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@ComponentScan(basePackages = "com.camper.yantarniytelegrambot")
public class BotConfig {
    @Bean
    public YantarniyTelegramBot telegramBot(@Value("${telegrambot.webHookPath}")String webHookPath,
                                            @Value("${telegrambot.username}") String username,
                                            @Value("${telegrambot.token}") String token) throws TelegramApiException {
        YantarniyTelegramBot bot = new YantarniyTelegramBot(new DefaultBotOptions(),webHookPath,username,token);
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(bot);
        return bot;
    }



    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:replies");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}

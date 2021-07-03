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

@Configuration
@ComponentScan(basePackages = "com.camper.yantarniytelegrambot")
public class BotConfig {
    @Bean
    public YantarniyTelegramBot telegramBot(@Value("${telegrambot.webHookPath}")String webHookPath,
                                            @Value("${telegrambot.username}") String username,
                                            @Value("${telegrambot.token}") String token) {
        return new YantarniyTelegramBot(new DefaultBotOptions(),webHookPath,username,token);
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:replies");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}

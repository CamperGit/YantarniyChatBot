package com.camper.yantarniytelegrambot.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MessageSourceService {
    private final Locale locale;
    private final MessageSource messageSource;

    public MessageSourceService(@Value("localeTag") String locale, MessageSource messageSource) {
        this.locale = Locale.forLanguageTag(locale);
        this.messageSource = messageSource;
    }

    public String getMessage(String key) {
        return messageSource.getMessage(key,null,locale);
    }
}

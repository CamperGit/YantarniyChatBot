package com.camper.yantarniytelegrambot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Component
public class LocaleMessageSource {
    private final Locale locale;
    private final MessageSource messageSource;

    public LocaleMessageSource(@Value("${localeTag}") String localeTag, MessageSource messageSource) {
        this.locale = Locale.forLanguageTag(localeTag);
        this.messageSource = messageSource;
    }

    public String getMessage(String key) {
        return messageSource.getMessage(key,null,locale);
    }
}

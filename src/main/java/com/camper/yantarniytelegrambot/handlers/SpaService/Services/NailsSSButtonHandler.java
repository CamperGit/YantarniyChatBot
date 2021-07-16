package com.camper.yantarniytelegrambot.handlers.SpaService.Services;

import com.camper.yantarniytelegrambot.entity.SpaService;
import com.camper.yantarniytelegrambot.enums.ScrollState;
import com.camper.yantarniytelegrambot.handlers.BotButtonHandler;
import com.camper.yantarniytelegrambot.services.LocaleMessageSource;
import com.camper.yantarniytelegrambot.services.SpaServService;
import com.camper.yantarniytelegrambot.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Component
public class NailsSSButtonHandler extends AbstractSpaServiceButtonHandler {
    @Override
    protected String getLocationName() {
        return "NAILS";
    }

    protected InlineKeyboardMarkup getPriceScrollMarkup(int numberOfCategory) {
        return BotButtonHandler.getScrollMenuMarkup(numberOfCategory, currentPage,
                "handleSSNailsPricePrevButton",
                "handleSSNailsPriceNextButton",
                "handleSpaServiceMenuButton",
                localeMessageSource.getMessage("spa.contactUs"),
                "handleSSContactUsButton");
    }
}

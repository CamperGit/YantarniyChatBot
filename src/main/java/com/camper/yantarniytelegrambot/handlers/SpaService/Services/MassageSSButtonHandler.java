package com.camper.yantarniytelegrambot.handlers.SpaService.Services;

import com.camper.yantarniytelegrambot.entity.SpaService;
import com.camper.yantarniytelegrambot.handlers.BotButtonHandler;
import com.camper.yantarniytelegrambot.utils.Utils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Component
public class MassageSSButtonHandler extends AbstractSpaServiceButtonHandler {
    @Override
    protected String getLocationName() {
        return "MASSAGE";
    }

    protected InlineKeyboardMarkup getPriceScrollMarkup(int numberOfCategory) {
        return BotButtonHandler.getScrollMenuMarkup(numberOfCategory, currentPage,
                "handleSSMassagePricePrevButton",
                "handleSSMassagePriceNextButton",
                "handleSpaServiceMenuButton",
                localeMessageSource.getMessage("spa.contactUs"),
                "handleSSContactUsButton");
    }
}

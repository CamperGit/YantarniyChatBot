package com.camper.yantarniytelegrambot.handlers.SpaService.Services;

import com.camper.yantarniytelegrambot.handlers.BotButtonHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

@Component
public class BathhouseSSButtonHandler extends AbstractSpaServiceButtonHandler {

    @Override
    protected String getLocationName() {
        return "BATHHOUSE";
    }

    @Override
    protected InlineKeyboardMarkup getPriceScrollMarkup(int numberOfCategory) {
        return BotButtonHandler.getScrollMenuMarkup(numberOfCategory, currentPage,
                "handleSSBathhousePricePrevButton",
                "handleSSBathhousePriceNextButton",
                "handleSpaServiceMenuButton",
                localeMessageSource.getMessage("spa.contactUs"),
                "handleSSContactUsButton");
    }
}

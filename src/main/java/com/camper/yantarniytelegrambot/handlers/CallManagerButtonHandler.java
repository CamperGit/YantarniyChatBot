package com.camper.yantarniytelegrambot.handlers;

import com.camper.yantarniytelegrambot.entity.CardType;
import com.camper.yantarniytelegrambot.services.CardTypeService;
import com.camper.yantarniytelegrambot.services.LocaleMessageSource;
import com.camper.yantarniytelegrambot.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class CallManagerButtonHandler implements BotButtonHandler {
    private LocaleMessageSource localeMessageSource;

    @Override
    public List<PartialBotApiMethod<?>> handle(String chatId, CallbackQuery query) {
        return new ArrayList<>(Collections.singletonList(Utils.changeMessage(localeMessageSource.getMessage("other.callManager"), chatId,query.getMessage().getMessageId(),getCallManagerMarkup())));
    }

    private InlineKeyboardMarkup getCallManagerMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton exitButton = new InlineKeyboardButton(localeMessageSource.getMessage("other.moveBack"));
        exitButton.setCallbackData("handleReturnMainMenuButton");
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(exitButton);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(Collections.singletonList(firstRow));
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    @Autowired
    public void setLocaleMessageSource(LocaleMessageSource localeMessageSource) {
        this.localeMessageSource = localeMessageSource;
    }
}

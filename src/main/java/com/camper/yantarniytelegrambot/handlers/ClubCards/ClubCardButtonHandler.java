package com.camper.yantarniytelegrambot.handlers.ClubCards;

import com.camper.yantarniytelegrambot.handlers.BotButtonHandler;
import com.camper.yantarniytelegrambot.services.LocaleMessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ClubCardButtonHandler implements BotButtonHandler {
    private LocaleMessageSource localeMessageSource;

    @Override
    public List<PartialBotApiMethod<?>> handle(String chatId, CallbackQuery query) {
        List<PartialBotApiMethod<?>> answers = new ArrayList<>();

        EditMessageText newText = new EditMessageText();
        newText.setText(localeMessageSource.getMessage("onAction.clubCardsButton"));
        newText.setChatId(chatId);
        newText.setMessageId(query.getMessage().getMessageId());
        newText.setReplyMarkup(getClubCartsMenuMarkup());

        answers.add(newText);
        return answers;
    }

    private InlineKeyboardMarkup getClubCartsMenuMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton typesButton = new InlineKeyboardButton(localeMessageSource.getMessage("clubCardMenu.typesButton"));
        InlineKeyboardButton salesButton = new InlineKeyboardButton(localeMessageSource.getMessage("clubCardMenu.salesButton"));
        InlineKeyboardButton exitButton = new InlineKeyboardButton(localeMessageSource.getMessage("other.moveBack"));

        typesButton.setCallbackData("handleClubCardsTypesButton");
        salesButton.setCallbackData("handleClubCardsSalesButton");
        exitButton.setCallbackData("handleClubCardsReturnButton");

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(typesButton);
        firstRow.add(salesButton);

        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        secondRow.add(exitButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(Arrays.asList(firstRow, secondRow));

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    @Autowired
    public void setLocaleMessageSource(LocaleMessageSource localeMessageSource) {
        this.localeMessageSource = localeMessageSource;
    }
}

package com.camper.yantarniytelegrambot.handlers;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public interface BotButtonHandler {
    List<PartialBotApiMethod<?>> handle(String chatId, CallbackQuery query);

    static InlineKeyboardMarkup getScrollMenuMarkup(int numberOfItems, int currentItem,
                                                    String prevButCallbackData, String nextButCallbackData, String exitButCallbackData,
                                                    String textOfContactUsButton, String contactUsCallbackData) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton prevButton = new InlineKeyboardButton("<--");
        InlineKeyboardButton countButton = new InlineKeyboardButton((numberOfItems == 0 ? 0 : currentItem) + "/" + numberOfItems);
        InlineKeyboardButton nextButton = new InlineKeyboardButton("-->");
        InlineKeyboardButton returnButton = new InlineKeyboardButton("Назад");
        InlineKeyboardButton mainMenuButton = new InlineKeyboardButton("В меню");

        prevButton.setCallbackData(prevButCallbackData);
        nextButton.setCallbackData(nextButCallbackData);
        countButton.setCallbackData("null");
        returnButton.setCallbackData(exitButCallbackData);
        mainMenuButton.setCallbackData("handleReturnMainMenuButton");

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(prevButton);
        firstRow.add(countButton);
        firstRow.add(nextButton);

        List<InlineKeyboardButton> thirdRow = new ArrayList<>();
        thirdRow.add(returnButton);
        thirdRow.add(mainMenuButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        rowList.add(firstRow);
        if (textOfContactUsButton != null && contactUsCallbackData != null) {
            InlineKeyboardButton contactUsButton = new InlineKeyboardButton(textOfContactUsButton);
            contactUsButton.setCallbackData(contactUsCallbackData);
            List<InlineKeyboardButton> secondRow = new ArrayList<>();
            secondRow.add(contactUsButton);
            rowList.add(secondRow);
        }
        rowList.add(thirdRow);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    static InlineKeyboardMarkup getReturnMarkup(String exitCallbackData, boolean createMainMenuButton) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton exitButton = new InlineKeyboardButton("Назад");
        exitButton.setCallbackData(exitCallbackData);
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(exitButton);
        if (createMainMenuButton) {
            InlineKeyboardButton mainMenuButton = new InlineKeyboardButton("В меню");
            mainMenuButton.setCallbackData("handleReturnMainMenuButton");
            firstRow.add(mainMenuButton);
        }
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(Collections.singletonList(firstRow));
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }
}

package com.camper.yantarniytelegrambot.handlers.Employees;

import com.camper.yantarniytelegrambot.handlers.BotButtonHandler;
import com.camper.yantarniytelegrambot.services.LocaleMessageSource;
import com.camper.yantarniytelegrambot.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class EmployeeMenuButtonHandler implements BotButtonHandler {
    private LocaleMessageSource localeMessageSource;

    @Override
    public List<PartialBotApiMethod<?>> handle(String chatId, CallbackQuery query) {
        return new ArrayList<>(Utils.changeMessage(localeMessageSource.getMessage("onAction.employees"),
                chatId,
                query.getMessage(),
                getEmployeesMenuMarkup()));
    }

    private InlineKeyboardMarkup getEmployeesMenuMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton coachesButton = new InlineKeyboardButton(localeMessageSource.getMessage("fitness.coaches"));
        InlineKeyboardButton specialistsButton = new InlineKeyboardButton(localeMessageSource.getMessage("spa.specialists"));
        InlineKeyboardButton exitButton = new InlineKeyboardButton(localeMessageSource.getMessage("other.moveBack"));

        coachesButton.setCallbackData("handleFitnessButton");
        specialistsButton.setCallbackData("handleSpaSpecialistsButton");
        exitButton.setCallbackData("handleReturnMainMenuButton");

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(coachesButton);
        firstRow.add(specialistsButton);

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

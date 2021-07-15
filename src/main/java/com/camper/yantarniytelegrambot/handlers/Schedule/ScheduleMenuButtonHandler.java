package com.camper.yantarniytelegrambot.handlers.Schedule;

import com.camper.yantarniytelegrambot.handlers.BotButtonHandler;
import com.camper.yantarniytelegrambot.services.LocaleMessageSource;
import com.camper.yantarniytelegrambot.utils.Utils;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ScheduleMenuButtonHandler implements BotButtonHandler {
    private LocaleMessageSource localeMessageSource;
    @Getter
    private SchedulesButtonHandler schedulesButtonHandler;
    @Getter
    private ScheduleChangesButtonHandler scheduleChangesButtonHandler;

    @Override
    public List<PartialBotApiMethod<?>> handle(String chatId, CallbackQuery query) {
        return new ArrayList<>(Arrays.asList(Utils.deleteMessage(chatId, query.getMessage().getMessageId()),
                SendMessage.builder()
                        .chatId(chatId)
                        .text(localeMessageSource.getMessage("onAction.fitnessScheduleButton"))
                        .replyMarkup(getSchedulesMenuMarkup())
                        .build()));
    }

    private InlineKeyboardMarkup getSchedulesMenuMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton groupsSchedulesButton = new InlineKeyboardButton(localeMessageSource.getMessage("fitness.schedules.groupsCourses"));
        InlineKeyboardButton changesInSchedulesButton = new InlineKeyboardButton(localeMessageSource.getMessage("fitness.schedules.changes"));
        InlineKeyboardButton returnButton = new InlineKeyboardButton(localeMessageSource.getMessage("other.moveBack"));

        groupsSchedulesButton.setCallbackData("handleFitnessCurrentScheduleButton");
        changesInSchedulesButton.setCallbackData("handleFitnessChangesButton");
        returnButton.setCallbackData("handleReturnMainMenuButton");

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(groupsSchedulesButton);
        firstRow.add(changesInSchedulesButton);

        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        secondRow.add(returnButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(Arrays.asList(firstRow, secondRow));

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    @Autowired
    public void setLocaleMessageSource(LocaleMessageSource localeMessageSource) {
        this.localeMessageSource = localeMessageSource;
    }

    @Autowired
    public void setSchedulesButtonHandler(SchedulesButtonHandler schedulesButtonHandler) {
        this.schedulesButtonHandler = schedulesButtonHandler;
    }

    @Autowired
    public void setScheduleChangesButtonHandler(ScheduleChangesButtonHandler scheduleChangesButtonHandler) {
        this.scheduleChangesButtonHandler = scheduleChangesButtonHandler;
    }
}

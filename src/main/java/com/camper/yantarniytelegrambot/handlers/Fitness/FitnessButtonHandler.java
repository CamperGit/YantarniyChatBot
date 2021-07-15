package com.camper.yantarniytelegrambot.handlers.Fitness;

import com.camper.yantarniytelegrambot.handlers.BotButtonHandler;
import com.camper.yantarniytelegrambot.handlers.Fitness.Coaches.FitnessContactUsButtonHandler;
import com.camper.yantarniytelegrambot.handlers.Fitness.Coaches.FitnessGroupActivityButtonHandler;
import com.camper.yantarniytelegrambot.handlers.Fitness.Coaches.FitnessGymButtonHandler;
import com.camper.yantarniytelegrambot.handlers.Fitness.Coaches.FitnessPoolButtonHandler;
import com.camper.yantarniytelegrambot.handlers.Schedule.ScheduleChangesButtonHandler;
import com.camper.yantarniytelegrambot.handlers.Schedule.SchedulesButtonHandler;
import com.camper.yantarniytelegrambot.services.LocaleMessageSource;
import com.camper.yantarniytelegrambot.utils.Utils;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
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
public class FitnessButtonHandler implements BotButtonHandler {
    private LocaleMessageSource localeMessageSource;
    @Getter
    private FitnessGroupActivityButtonHandler fitnessGroupActivityButtonHandler;
    @Getter
    private FitnessGymButtonHandler fitnessGymButtonHandler;
    @Getter
    private FitnessPoolButtonHandler fitnessPoolButtonHandler;
    @Getter
    private FitnessContactUsButtonHandler fitnessContactUsButtonHandler;

    @Override
    public List<PartialBotApiMethod<?>> handle(String chatId, CallbackQuery query) {
        return new ArrayList<>(Arrays.asList(Utils.deleteMessage(chatId, query.getMessage().getMessageId()),
                SendMessage.builder()
                        .chatId(chatId)
                        .text(localeMessageSource.getMessage("onAction.fitnessCoachesButton"))
                        .replyMarkup(getCoachesMenuMarkup())
                        .build()));
    }

    /*public List<PartialBotApiMethod<?>> openCoachesMenu(String chatId, CallbackQuery query) {
        return new ArrayList<>(Arrays.asList(Utils.deleteMessage(chatId, query.getMessage().getMessageId()),
                SendMessage.builder()
                        .chatId(chatId)
                        .text(localeMessageSource.getMessage("onAction.fitnessCoachesButton"))
                        .replyMarkup(getCoachesMenuMarkup())
                        .build()));
    }

    public List<PartialBotApiMethod<?>> openSchedulesMenu(String chatId, CallbackQuery query) {
        return new ArrayList<>(Arrays.asList(Utils.deleteMessage(chatId, query.getMessage().getMessageId()),
                SendMessage.builder()
                        .chatId(chatId)
                        .text(localeMessageSource.getMessage("onAction.fitnessScheduleButton"))
                        .replyMarkup(getSchedulesMenuMarkup())
                        .build()));
    }*/

    private InlineKeyboardMarkup getCoachesMenuMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton gymButton = new InlineKeyboardButton(localeMessageSource.getMessage("fitness.coaches.gym"));
        InlineKeyboardButton groupsButton = new InlineKeyboardButton(localeMessageSource.getMessage("fitness.coaches.groups"));
        InlineKeyboardButton poolButton = new InlineKeyboardButton(localeMessageSource.getMessage("fitness.coaches.pool"));
        InlineKeyboardButton returnButton = new InlineKeyboardButton(localeMessageSource.getMessage("other.moveBack"));

        gymButton.setCallbackData("handleFitnessGymButton");
        groupsButton.setCallbackData("handleFitnessGroupsButton");
        poolButton.setCallbackData("handleFitnessPoolButton");
        returnButton.setCallbackData("handleReturnMainMenuButton");

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(gymButton);
        firstRow.add(poolButton);

        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        secondRow.add(groupsButton);

        List<InlineKeyboardButton> thirdRow = new ArrayList<>();
        thirdRow.add(returnButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(Arrays.asList(firstRow, secondRow, thirdRow));

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    @Autowired
    public void setLocaleMessageSource(LocaleMessageSource localeMessageSource) {
        this.localeMessageSource = localeMessageSource;
    }

    @Autowired
    public void setFitnessGroupActivityButtonHandler(FitnessGroupActivityButtonHandler fitnessGroupActivityButtonHandler) {
        this.fitnessGroupActivityButtonHandler = fitnessGroupActivityButtonHandler;
    }

    @Autowired
    public void setFitnessGymButtonHandler(FitnessGymButtonHandler fitnessGymButtonHandler) {
        this.fitnessGymButtonHandler = fitnessGymButtonHandler;
    }

    @Autowired
    public void setFitnessPoolButtonHandler(FitnessPoolButtonHandler fitnessPoolButtonHandler) {
        this.fitnessPoolButtonHandler = fitnessPoolButtonHandler;
    }

    @Autowired
    public void setFitnessContactUsButtonHandler(FitnessContactUsButtonHandler fitnessContactUsButtonHandler) {
        this.fitnessContactUsButtonHandler = fitnessContactUsButtonHandler;
    }
}

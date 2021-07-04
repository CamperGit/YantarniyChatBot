package com.camper.yantarniytelegrambot.handlers.Fitness;

import com.camper.yantarniytelegrambot.entity.Employee;
import com.camper.yantarniytelegrambot.entity.Schedule;
import com.camper.yantarniytelegrambot.handlers.BotButtonHandler;
import com.camper.yantarniytelegrambot.services.LocaleMessageSource;
import com.camper.yantarniytelegrambot.services.ScheduleService;
import com.camper.yantarniytelegrambot.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class FitnessChangesButtonHandler implements BotButtonHandler {
    private LocaleMessageSource localeMessageSource;
    private ScheduleService scheduleService;
    private int currentPage = 1;
    private List<Schedule> changes = null;

    @Override
    public List<PartialBotApiMethod<?>> handle(String chatId, CallbackQuery query) {
        changes = scheduleService.findAllScheduleChanges();
        currentPage = 1;

        List<PartialBotApiMethod<?>> answers = new ArrayList<>();

        if (changes != null && !changes.isEmpty()) {
            Schedule selectedChange = changes.get(0);

            if (selectedChange.getImage() != null) {
                SendPhoto.SendPhotoBuilder builder = SendPhoto.builder();
                builder.chatId(chatId);
                builder.photo(new InputFile(new ByteArrayInputStream(selectedChange.getImage()), "filename"));
                builder.replyMarkup(getScheduleChangesMarkup(changes.size()));
                SendPhoto sendPhoto = builder.build();
                answers.add(sendPhoto);
            } else {
                SendMessage sendMessage = new SendMessage(chatId, selectedChange.getDescription());
                sendMessage.setReplyMarkup(getScheduleChangesMarkup(changes.size()));
                answers.add(sendMessage);
            }
        } else {
            SendMessage sendMessage = new SendMessage(chatId, localeMessageSource.getMessage("fitness.schedulesChanges.empty"));
            sendMessage.setReplyMarkup(getChangesEmptyMarkup());
            answers.add(sendMessage);
        }

        answers.add(Utils.deleteMessage(chatId, query.getMessage().getMessageId()));

        return answers;
    }

    public List<PartialBotApiMethod<?>> nextChange(String chatId, CallbackQuery query) {
        if (changes == null) {
            changes = scheduleService.findAllScheduleChanges();
        }
        if (changes.isEmpty() || currentPage == changes.size()) {
            return null;
        }
        currentPage++;
        Schedule selectedChange = changes.get(currentPage - 1);
        Integer messageId = query.getMessage().getMessageId();

        return new ArrayList<>(Utils.scrollMenuItem(chatId
                , messageId
                , query
                , getScheduleChangesMarkup(changes.size())
                , selectedChange.getImage()
                , selectedChange.getDescription()));
    }

    public List<PartialBotApiMethod<?>> previousChange(String chatId, CallbackQuery query) {
        if (changes == null) {
            changes = scheduleService.findAllScheduleChanges();
        }
        if (changes.isEmpty() || currentPage == 1) {
            return null;
        }
        currentPage--;
        Schedule selectedChange = changes.get(currentPage - 1);
        Integer messageId = query.getMessage().getMessageId();

        return new ArrayList<>(Utils.scrollMenuItem(chatId
                , messageId
                , query
                , getScheduleChangesMarkup(changes.size())
                , selectedChange.getImage()
                , selectedChange.getDescription()));
    }

    private InlineKeyboardMarkup getScheduleChangesMarkup(int numberOfCoaches) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton prevButton = new InlineKeyboardButton("<--");
        InlineKeyboardButton countButton = new InlineKeyboardButton((numberOfCoaches == 0 ? 0 : currentPage) + "/" + numberOfCoaches);
        InlineKeyboardButton nextButton = new InlineKeyboardButton("-->");
        InlineKeyboardButton returnButton = new InlineKeyboardButton(localeMessageSource.getMessage("other.moveBack"));

        prevButton.setCallbackData("handleFitnessChangePrevButton");
        nextButton.setCallbackData("handleFitnessChangeNextButton");
        countButton.setCallbackData("null");
        returnButton.setCallbackData("handleFitnessSchedulesButton");

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(prevButton);
        firstRow.add(countButton);
        firstRow.add(nextButton);

        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        secondRow.add(returnButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(Arrays.asList(firstRow, secondRow));

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    private InlineKeyboardMarkup getChangesEmptyMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton exitButton = new InlineKeyboardButton(localeMessageSource.getMessage("other.moveBack"));
        exitButton.setCallbackData("handleFitnessSchedulesButton");
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

    @Autowired
    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }
}
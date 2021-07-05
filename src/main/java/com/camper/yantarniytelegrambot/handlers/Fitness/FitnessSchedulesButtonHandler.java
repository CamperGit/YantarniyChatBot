package com.camper.yantarniytelegrambot.handlers.Fitness;

import com.camper.yantarniytelegrambot.entity.Schedule;
import com.camper.yantarniytelegrambot.enums.ScheduleType;
import com.camper.yantarniytelegrambot.handlers.BotButtonHandler;
import com.camper.yantarniytelegrambot.services.LocaleMessageSource;
import com.camper.yantarniytelegrambot.services.ScheduleService;
import com.camper.yantarniytelegrambot.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class FitnessSchedulesButtonHandler implements BotButtonHandler {
    private LocaleMessageSource localeMessageSource;
    private ScheduleService scheduleService;


    @Override
    public List<PartialBotApiMethod<?>> handle(String chatId, CallbackQuery query) {
        List<PartialBotApiMethod<?>> answers = new ArrayList<>();
        answers.add(Utils.deleteMessage(chatId,query.getMessage().getMessageId()));

        Schedule schedule = scheduleService.findByType(ScheduleType.DEFAULT);
        SendPhoto.SendPhotoBuilder builder = SendPhoto.builder();
        builder.chatId(chatId);
        builder.photo(new InputFile(new ByteArrayInputStream(schedule.getImage()), "filename"));
        builder.replyMarkup(BotButtonHandler.getReturnMarkup("handleFitnessSchedulesButton"));
        builder.caption(localeMessageSource.getMessage("fitness.schedules.current"));
        answers.add(builder.build());
        return answers;
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

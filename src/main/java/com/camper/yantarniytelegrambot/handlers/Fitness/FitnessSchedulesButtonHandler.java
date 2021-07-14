package com.camper.yantarniytelegrambot.handlers.Fitness;

import com.camper.yantarniytelegrambot.entity.Schedule;
import com.camper.yantarniytelegrambot.enums.ScheduleType;
import com.camper.yantarniytelegrambot.enums.ScrollState;
import com.camper.yantarniytelegrambot.handlers.BotButtonHandler;
import com.camper.yantarniytelegrambot.services.LocaleMessageSource;
import com.camper.yantarniytelegrambot.services.ScheduleService;
import com.camper.yantarniytelegrambot.utils.Utils;
import com.google.common.io.ByteStreams;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class FitnessSchedulesButtonHandler implements BotButtonHandler {
    private LocaleMessageSource localeMessageSource;
    private ScheduleService scheduleService;
    private int currentPage = 1;
    private List<byte[]> images = null;

    @SneakyThrows
    @Override
    public List<PartialBotApiMethod<?>> handle(String chatId, CallbackQuery query) {
        images = getScheduleImages();
        currentPage = 1;

        List<PartialBotApiMethod<?>> answers = new ArrayList<>();
        answers.add(Utils.deleteMessage(chatId,query.getMessage().getMessageId()));

        if (images != null && !images.isEmpty()) {
            SendPhoto.SendPhotoBuilder builder = SendPhoto.builder();
            builder.chatId(chatId);
            builder.photo(new InputFile(new ByteArrayInputStream(images.get(0)), "filename"));
            builder.replyMarkup(getScheduleMarkup());
            builder.caption(localeMessageSource.getMessage("fitness.schedules.current"));
            answers.add(builder.build());
        }
        return answers;
    }

    public List<PartialBotApiMethod<?>> scrollSchedule(String chatId, CallbackQuery query, ScrollState state) throws IOException {
        if (images == null) {
            images = getScheduleImages();
        }
        if (state.equals(ScrollState.NEXT)) {
            if (images.size() == 1 || currentPage == images.size()) {
                return null;
            }
            currentPage++;
        } else {
            if (images.size() == 1 || currentPage == 1) {
                return null;
            }
            currentPage--;
        }

        byte[] selectedImage = images.get(currentPage - 1);

        return new ArrayList<>(Utils.scrollMenuItem(chatId,
                query.getMessage(),
                query,
                getScheduleMarkup(),
                selectedImage,
                currentPage == 1 ? localeMessageSource.getMessage("fitness.schedules.current") : localeMessageSource.getMessage("fitness.schedules.description")));
    }

    private InlineKeyboardMarkup getScheduleMarkup() {
        return BotButtonHandler.getScrollMenuMarkup(2,currentPage,
                "handleSchedulePrevButton",
                "handleScheduleNextButton",
                "handleFitnessSchedulesButton",
                null,
                null);
    }

    private List<byte[]> getScheduleImages() throws IOException {
        List<byte[]> result = new ArrayList<>();
        Schedule schedule = scheduleService.findByType(ScheduleType.DEFAULT);
        result.add(schedule.getImage());
        result.add(ByteStreams.toByteArray(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream("other/scheduleDescription.jpeg"))));
        return result;
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

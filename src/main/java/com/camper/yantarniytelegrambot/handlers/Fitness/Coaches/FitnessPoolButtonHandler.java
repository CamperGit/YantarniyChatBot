package com.camper.yantarniytelegrambot.handlers.Fitness.Coaches;

import com.camper.yantarniytelegrambot.entity.Employee;
import com.camper.yantarniytelegrambot.entity.Location;
import com.camper.yantarniytelegrambot.enums.ScrollState;
import com.camper.yantarniytelegrambot.handlers.BotButtonHandler;
import com.camper.yantarniytelegrambot.services.EmployeeService;
import com.camper.yantarniytelegrambot.services.LocaleMessageSource;
import com.camper.yantarniytelegrambot.services.LocationService;
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
import java.util.List;

@Component
public class FitnessPoolButtonHandler extends FitnessCoachesButtonHandler {

    @Override
    public List<PartialBotApiMethod<?>> handle(String chatId, CallbackQuery query) {
        coaches = employeeService.findAllByLocation(location);
        currentPage = 1;

        List<PartialBotApiMethod<?>> answers = new ArrayList<>();

        if (coaches != null && !coaches.isEmpty()) {
            Employee selectedCoach = coaches.get(0);

            if (selectedCoach.getImage() != null) {
                SendPhoto.SendPhotoBuilder builder = SendPhoto.builder();
                builder.chatId(chatId);
                builder.photo(new InputFile(new ByteArrayInputStream(selectedCoach.getImage()), "filename"));
                builder.replyMarkup(getCoachesMarkup(coaches.size()));

                String type = selectedCoach.getEmployeeType().getTitle();
                if (type != null) {
                    builder.caption(localeMessageSource.getMessage("fitness.coaches.category") + " " + type);
                }

                SendPhoto sendPhoto = builder.build();
                answers.add(sendPhoto);
            } else {
                SendMessage sendMessage = new SendMessage(chatId, selectedCoach.getDescription());
                sendMessage.setReplyMarkup(getCoachesMarkup(coaches.size()));
                answers.add(sendMessage);
            }
        }

        answers.add(Utils.deleteMessage(chatId, query.getMessage().getMessageId()));

        return answers;
    }

    @Override
    protected InlineKeyboardMarkup getCoachesMarkup(int numberOfCoaches) {
        return BotButtonHandler.getScrollMenuMarkup(numberOfCoaches, currentPage,
                "handleFitnessPoolPrevButton",
                "handleFitnessPoolNextButton",
                "handleFitnessButton",
                localeMessageSource.getMessage("fitness.coaches.contactUs"),
                "handleFitnessContactUsButton");
    }

    @Autowired
    public void setLocationService(LocationService locationService) {
        location = locationService.findLocationByTitle("POOL");
    }
}

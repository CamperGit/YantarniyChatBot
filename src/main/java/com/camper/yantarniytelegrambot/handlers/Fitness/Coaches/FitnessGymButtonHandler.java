package com.camper.yantarniytelegrambot.handlers.Fitness.Coaches;

import com.camper.yantarniytelegrambot.entity.Employee;
import com.camper.yantarniytelegrambot.entity.Location;
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
public class FitnessGymButtonHandler implements BotButtonHandler {

    private LocaleMessageSource localeMessageSource;
    private EmployeeService employeeService;
    private int currentPage = 1;
    private List<Employee> coaches = null;
    private Location location;

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
                builder.replyMarkup(getGymCoachesMarkup(coaches.size()));

                String type = selectedCoach.getEmployeeType().getTitle();
                if (type != null) {
                    builder.caption(localeMessageSource.getMessage("fitness.coaches.category") + " " + type);
                }

                SendPhoto sendPhoto = builder.build();
                answers.add(sendPhoto);
            } else {
                SendMessage sendMessage = new SendMessage(chatId, selectedCoach.getDescription());
                sendMessage.setReplyMarkup(getGymCoachesMarkup(coaches.size()));
                answers.add(sendMessage);
            }
        }

        answers.add(Utils.deleteMessage(chatId, query.getMessage().getMessageId()));

        return answers;
    }

    public List<PartialBotApiMethod<?>> nextCoach(String chatId, CallbackQuery query) {
        if (coaches == null) {
            coaches = employeeService.findAllByLocation(location);
        }
        if (coaches.isEmpty() || currentPage == coaches.size()) {
            return null;
        }
        currentPage++;
        Employee selectedCoach = coaches.get(currentPage - 1);
        Integer messageId = query.getMessage().getMessageId();

        String type = selectedCoach.getEmployeeType().getTitle();
        String description = localeMessageSource.getMessage("fitness.coaches.category") + " " + type;

        return new ArrayList<>(Utils.scrollMenuItem(chatId
                , messageId
                , query
                , getGymCoachesMarkup(coaches.size())
                , selectedCoach.getImage()
                , description));
    }

    public List<PartialBotApiMethod<?>> previousCoach(String chatId, CallbackQuery query) {
        if (coaches == null) {
            coaches = employeeService.findAllByLocation(location);
        }
        if (coaches.isEmpty() || currentPage == 1) {
            return null;
        }
        currentPage--;
        Employee selectedCoach = coaches.get(currentPage - 1);
        Integer messageId = query.getMessage().getMessageId();

        String type = selectedCoach.getEmployeeType().getTitle();
        String description = localeMessageSource.getMessage("fitness.coaches.category") + " " + type;

        return new ArrayList<>(Utils.scrollMenuItem(chatId
                , messageId
                , query
                , getGymCoachesMarkup(coaches.size())
                , selectedCoach.getImage()
                , description));
    }

    private InlineKeyboardMarkup getGymCoachesMarkup(int numberOfCoaches) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton prevButton = new InlineKeyboardButton("<--");
        InlineKeyboardButton countButton = new InlineKeyboardButton((numberOfCoaches == 0 ? 0 : currentPage) + "/" + numberOfCoaches);
        InlineKeyboardButton nextButton = new InlineKeyboardButton("-->");
        InlineKeyboardButton returnButton = new InlineKeyboardButton(localeMessageSource.getMessage("other.moveBack"));

        prevButton.setCallbackData("handleFitnessGymPrevButton");
        nextButton.setCallbackData("handleFitnessGymNextButton");
        countButton.setCallbackData("null");
        returnButton.setCallbackData("handleFitnessCoachesButton");

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

    @Autowired
    public void setLocaleMessageSource(LocaleMessageSource localeMessageSource) {
        this.localeMessageSource = localeMessageSource;
    }

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Autowired
    public void setLocationService(LocationService locationService) {
        location = locationService.findLocationByTitle("GYM");
    }
}

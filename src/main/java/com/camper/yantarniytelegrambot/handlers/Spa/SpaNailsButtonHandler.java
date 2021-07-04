package com.camper.yantarniytelegrambot.handlers.Spa;

import com.camper.yantarniytelegrambot.entity.Employee;
import com.camper.yantarniytelegrambot.entity.EmployeeType;
import com.camper.yantarniytelegrambot.handlers.BotButtonHandler;
import com.camper.yantarniytelegrambot.services.EmployeeService;
import com.camper.yantarniytelegrambot.services.EmployeeTypeService;
import com.camper.yantarniytelegrambot.services.LocaleMessageSource;
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
public class SpaNailsButtonHandler implements BotButtonHandler {
    private LocaleMessageSource localeMessageSource;
    private EmployeeService employeeService;
    private int currentPage = 1;
    private List<Employee> specialists = null;
    private EmployeeType employeeType;

    @Override
    public List<PartialBotApiMethod<?>> handle(String chatId, CallbackQuery query) {
        specialists = employeeService.findAllByEmployeeType(employeeType);
        currentPage = 1;

        List<PartialBotApiMethod<?>> answers = new ArrayList<>();

        if (specialists != null && !specialists.isEmpty()) {
            Employee selectedCoach = specialists.get(0);

            if (selectedCoach.getImage() != null) {
                SendPhoto.SendPhotoBuilder builder = SendPhoto.builder();
                builder.chatId(chatId);
                builder.photo(new InputFile(new ByteArrayInputStream(selectedCoach.getImage()), "filename"));
                builder.replyMarkup(getNailsSpecialistsMarkup(specialists.size()));
                SendPhoto sendPhoto = builder.build();
                answers.add(sendPhoto);
            } else {
                SendMessage sendMessage = new SendMessage(chatId, selectedCoach.getDescription());
                sendMessage.setReplyMarkup(getNailsSpecialistsMarkup(specialists.size()));
                answers.add(sendMessage);
            }
        }

        answers.add(Utils.deleteMessage(chatId, query.getMessage().getMessageId()));

        return answers;
    }

    public List<PartialBotApiMethod<?>> nextSpecialist(String chatId, CallbackQuery query) {
        if (specialists == null) {
            specialists = employeeService.findAllByEmployeeType(employeeType);
        }
        if (specialists.isEmpty() || currentPage == specialists.size()) {
            return null;
        }
        currentPage++;
        Employee selectedSpecialist = specialists.get(currentPage - 1);
        Integer messageId = query.getMessage().getMessageId();

        return new ArrayList<>(Utils.scrollMenuItem(chatId
                , messageId
                , query
                , getNailsSpecialistsMarkup(specialists.size())
                , selectedSpecialist.getImage()
                , selectedSpecialist.getDescription()));
    }

    public List<PartialBotApiMethod<?>> previousSpecialist(String chatId, CallbackQuery query) {
        if (specialists == null) {
            specialists = employeeService.findAllByEmployeeType(employeeType);
        }
        if (specialists.isEmpty() || currentPage == 1) {
            return null;
        }
        currentPage--;
        Employee selectedSpecialist = specialists.get(currentPage - 1);
        Integer messageId = query.getMessage().getMessageId();

        return new ArrayList<>(Utils.scrollMenuItem(chatId
                , messageId
                , query
                , getNailsSpecialistsMarkup(specialists.size())
                , selectedSpecialist.getImage()
                , selectedSpecialist.getDescription()));
    }

    private InlineKeyboardMarkup getNailsSpecialistsMarkup(int numberOfCoaches) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton prevButton = new InlineKeyboardButton("<--");
        InlineKeyboardButton countButton = new InlineKeyboardButton((numberOfCoaches == 0 ? 0 : currentPage) + "/" + numberOfCoaches);
        InlineKeyboardButton nextButton = new InlineKeyboardButton("-->");
        InlineKeyboardButton returnButton = new InlineKeyboardButton(localeMessageSource.getMessage("other.moveBack"));

        prevButton.setCallbackData("handleSpaNailsPrevButton");
        nextButton.setCallbackData("handleSpaNailsNextButton");
        countButton.setCallbackData("null");
        returnButton.setCallbackData("handleSpaSpecialistsButton");

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
    public void setEmployeeTypeService(EmployeeTypeService employeeTypeService) {
        this.employeeType = employeeTypeService.findEmployeeTypeByType("NAILS_MASTER");
    }
}

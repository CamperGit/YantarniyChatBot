package com.camper.yantarniytelegrambot.handlers.Spa.Specialists;

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
public class SpaNailsButtonHandler extends SpaSpecialistsButtonHandler {

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
                builder.replyMarkup(getSpecialistsMarkup(specialists.size()));
                SendPhoto sendPhoto = builder.build();
                answers.add(sendPhoto);
            } else {
                SendMessage sendMessage = new SendMessage(chatId, selectedCoach.getDescription());
                sendMessage.setReplyMarkup(getSpecialistsMarkup(specialists.size()));
                answers.add(sendMessage);
            }
        }

        answers.add(Utils.deleteMessage(chatId, query.getMessage().getMessageId()));

        return answers;
    }

    @Override
    protected InlineKeyboardMarkup getSpecialistsMarkup(int numberOfSpecialists) {
        return BotButtonHandler.getScrollMenuMarkup(numberOfSpecialists, currentPage,
                "handleSpaNailsPrevButton",
                "handleSpaNailsNextButton",
                "handleSpaSpecialistsButton",
                localeMessageSource.getMessage("spa.contactUs"),
                "handleSpaContactUsButton");
    }

    @Autowired
    public void setEmployeeTypeService(EmployeeTypeService employeeTypeService) {
        this.employeeType = employeeTypeService.findEmployeeTypeByType("NAILS_MASTER");
    }
}

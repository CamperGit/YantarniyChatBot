package com.camper.yantarniytelegrambot.handlers.Spa.Specialists;

import com.camper.yantarniytelegrambot.entity.Employee;
import com.camper.yantarniytelegrambot.entity.EmployeeType;
import com.camper.yantarniytelegrambot.enums.ScrollState;
import com.camper.yantarniytelegrambot.handlers.BotButtonHandler;
import com.camper.yantarniytelegrambot.services.EmployeeService;
import com.camper.yantarniytelegrambot.services.EmployeeTypeService;
import com.camper.yantarniytelegrambot.services.LocaleMessageSource;
import com.camper.yantarniytelegrambot.services.LocationService;
import com.camper.yantarniytelegrambot.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;

@Component
public abstract class SpaSpecialistsButtonHandler implements BotButtonHandler {
    protected LocaleMessageSource localeMessageSource;
    protected EmployeeService employeeService;
    protected int currentPage = 1;
    protected List<Employee> specialists = null;
    protected EmployeeType employeeType;

    public List<PartialBotApiMethod<?>> scrollItem(String chatId, CallbackQuery query, ScrollState scrollState) {
        if (specialists == null) {
            specialists = employeeService.findAllByEmployeeType(employeeType);
        }
        if (scrollState.equals(ScrollState.NEXT)) {
            if (specialists.isEmpty() || currentPage == specialists.size()) {
                return null;
            }
            currentPage++;
        } else {
            if (specialists.isEmpty() || currentPage == 1) {
                return null;
            }
            currentPage--;
        }

        Employee selectedSpecialist = specialists.get(currentPage - 1);
        Integer messageId = query.getMessage().getMessageId();

        return new ArrayList<>(Utils.scrollMenuItem(chatId
                , messageId
                , query
                , getSpecialistsMarkup(specialists.size())
                , selectedSpecialist.getImage()
                , selectedSpecialist.getDescription()));
    }

    protected abstract InlineKeyboardMarkup getSpecialistsMarkup(int numberOfSpecialists);

    @Autowired
    public void setLocaleMessageSource(LocaleMessageSource localeMessageSource) {
        this.localeMessageSource = localeMessageSource;
    }

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public abstract void setEmployeeTypeService(EmployeeTypeService employeeTypeService);
}

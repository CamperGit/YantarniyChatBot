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
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;

@Component
public abstract class FitnessCoachesButtonHandler implements BotButtonHandler {
    protected LocaleMessageSource localeMessageSource;
    protected EmployeeService employeeService;
    protected int currentPage = 1;
    protected List<Employee> coaches = null;
    protected Location location;

    public List<PartialBotApiMethod<?>> scrollItem(String chatId, CallbackQuery query, ScrollState scrollState) {
        if (coaches == null) {
            coaches = employeeService.findAllByLocation(location);
        }
        if (scrollState.equals(ScrollState.NEXT)) {
            if (coaches.isEmpty() || currentPage == coaches.size()) {
                return null;
            }
            currentPage++;
        } else {
            if (coaches.isEmpty() || currentPage == 1) {
                return null;
            }
            currentPage--;
        }
        Employee selectedCoach = coaches.get(currentPage - 1);

        String type = selectedCoach.getEmployeeType().getTitle();
        String description = localeMessageSource.getMessage("fitness.coaches.category") + " " + type;

        return new ArrayList<>(Utils.scrollMenuItem(chatId,
                query.getMessage(),
                query,
                getCoachesMarkup(coaches.size()),
                selectedCoach.getImage(),
                description));
    }

    protected abstract InlineKeyboardMarkup getCoachesMarkup(int numberOfCoaches);

    @Autowired
    public void setLocaleMessageSource(LocaleMessageSource localeMessageSource) {
        this.localeMessageSource = localeMessageSource;
    }

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public abstract void setLocationService(LocationService locationService);
}

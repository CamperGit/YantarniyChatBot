package com.camper.yantarniytelegrambot.handlers.SpaService.Services;

import com.camper.yantarniytelegrambot.entity.SpaService;
import com.camper.yantarniytelegrambot.enums.ScrollState;
import com.camper.yantarniytelegrambot.handlers.BotButtonHandler;
import com.camper.yantarniytelegrambot.services.LocaleMessageSource;
import com.camper.yantarniytelegrambot.services.SpaServService;
import com.camper.yantarniytelegrambot.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Component
public class NailsSSButtonHandler extends AbstractSpaServiceButtonHandler {
    @Override
    public List<PartialBotApiMethod<?>> handle(String chatId, CallbackQuery query) {
        currentPage = 1;
        List<SpaService> services = spaServService.findAll();
        servicesMap = new TreeMap<>(services.stream()
                .filter(s->s.getSpaServiceCategory()
                        .getLocation()
                        .getTitle().equals("NAILS"))
                .collect(Collectors.groupingByConcurrent(s->s.getSpaServiceCategory().getCategory())));

        if (servicesMap.size() != 0) {
            String categoryName = servicesMap.firstKey();
            List<SpaService> firstPage = servicesMap.get(categoryName);
            return Utils.changeMessage(createPriceMessage(categoryName, firstPage), chatId, query.getMessage(), getPriceScrollMarkup(servicesMap.keySet().size()));
        }
        return null;
    }

    protected InlineKeyboardMarkup getPriceScrollMarkup(int numberOfCategory) {
        return BotButtonHandler.getScrollMenuMarkup(numberOfCategory, currentPage,
                "handleSSNailsPricePrevButton",
                "handleSSNailsPriceNextButton",
                "handleSpaServiceMenuButton",
                localeMessageSource.getMessage("spa.contactUs"),
                "handleSSContactUsButton");
    }
}

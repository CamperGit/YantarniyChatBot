package com.camper.yantarniytelegrambot.handlers.SpaService.Services;

import com.camper.yantarniytelegrambot.entity.SpaService;
import com.camper.yantarniytelegrambot.handlers.BotButtonHandler;
import com.camper.yantarniytelegrambot.utils.Utils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Component
public class MassageSSButtonHandler extends AbstractSpaServiceButtonHandler {
    @Override
    public List<PartialBotApiMethod<?>> handle(String chatId, CallbackQuery query) {
        currentPage = 1;
        List<SpaService> services = spaServService.findAll();
        servicesMap = new TreeMap<>(services.stream()
                .filter(s->s.getSpaServiceCategory()
                        .getLocation()
                        .getTitle().equals("MASSAGE"))
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
                "handleSSMassagePricePrevButton",
                "handleSSMassagePriceNextButton",
                "handleSpaServiceMenuButton",
                localeMessageSource.getMessage("spa.contactUs"),
                "handleSSContactUsButton");
    }
}

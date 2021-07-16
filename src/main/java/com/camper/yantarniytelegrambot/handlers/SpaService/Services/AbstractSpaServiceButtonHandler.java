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

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Component
public abstract class AbstractSpaServiceButtonHandler implements BotButtonHandler {
    protected LocaleMessageSource localeMessageSource;
    protected SpaServService spaServService;
    protected NavigableMap<String, List<SpaService>> servicesMap = null;
    protected int currentPage = 1;

    public List<PartialBotApiMethod<?>> scrollPrice(String chatId, CallbackQuery query,ScrollState scrollState) {
        if (servicesMap == null) {
            List<SpaService> services = spaServService.findAll();
            servicesMap = new TreeMap<>(services.stream()
                    .filter(s->s.getSpaServiceCategory()
                            .getLocation()
                            .getTitle().equals(getLocationName()))
                    .collect(Collectors.groupingByConcurrent(s->s.getSpaServiceCategory().getCategory())));
        }
        if (scrollState.equals(ScrollState.NEXT)) {
            if (servicesMap.isEmpty() || currentPage == servicesMap.size()) {
                return null;
            }
            currentPage++;
        } else {
            if (servicesMap.isEmpty() || currentPage == 1) {
                return null;
            }
            currentPage--;
        }
        AtomicReference<String> currentCategory = new AtomicReference<>();

        AtomicInteger counter = new AtomicInteger(1);
        servicesMap.forEach((k,v)->{
            if (counter.get() == currentPage) {
                currentCategory.set(k);
                counter.incrementAndGet();
                return;
            }
            counter.incrementAndGet();
        });

        String description = createPriceMessage(currentCategory.get(), servicesMap.get(currentCategory.get()));

        return new ArrayList<>(Utils.scrollMenuItem(chatId,
                query.getMessage(),
                query,
                getPriceScrollMarkup(servicesMap.keySet().size()),
                null,
                description));
    }

    @Override
    public List<PartialBotApiMethod<?>> handle(String chatId, CallbackQuery query) {
        currentPage = 1;
        List<SpaService> services = spaServService.findAll();
        servicesMap = new TreeMap<>(services.stream()
                .filter(s->s.getSpaServiceCategory()
                        .getLocation()
                        .getTitle().equals(getLocationName()))
                .collect(Collectors.groupingByConcurrent(s->s.getSpaServiceCategory().getCategory())));

        if (servicesMap.size() != 0) {
            String categoryName = servicesMap.firstKey();
            List<SpaService> firstPage = servicesMap.get(categoryName);
            return Utils.changeMessage(createPriceMessage(categoryName, firstPage), chatId, query.getMessage(), getPriceScrollMarkup(servicesMap.keySet().size()));
        }
        return null;
    }

    protected String createPriceMessage(String category, List<SpaService> services) {
        StringBuilder priceMessageBuilder = new StringBuilder();
        priceMessageBuilder.append("<b>").append(category).append("</b>").append("\n");
        for (SpaService spaService : services) {
            priceMessageBuilder.append("<u>").append(spaService.getName()).append("</u>").append("\n");
            priceMessageBuilder.append(localeMessageSource.getMessage("spaServices.price")).append("\n");
            priceMessageBuilder.append(spaService.getPrice()).append("\n");
            String description = spaService.getDescription();
            if (description != null) {
                priceMessageBuilder.append(localeMessageSource.getMessage("spaServices.description")).append("\n");
                priceMessageBuilder.append(description).append("\n");
            }
            priceMessageBuilder.append("\n");
        }
        return priceMessageBuilder.toString();
    }

    protected abstract String getLocationName();
    protected abstract InlineKeyboardMarkup getPriceScrollMarkup(int numberOfCategory);

    @Autowired
    public void setLocaleMessageSource(LocaleMessageSource localeMessageSource) {
        this.localeMessageSource = localeMessageSource;
    }

    @Autowired
    public void setSpaServService(SpaServService spaServService) {
        this.spaServService = spaServService;
    }
}

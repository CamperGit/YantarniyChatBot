package com.camper.yantarniytelegrambot.handlers.SpaService.Services;

import com.camper.yantarniytelegrambot.entity.SpaPrice;
import com.camper.yantarniytelegrambot.enums.ScrollState;
import com.camper.yantarniytelegrambot.handlers.BotButtonHandler;
import com.camper.yantarniytelegrambot.services.LocaleMessageSource;
import com.camper.yantarniytelegrambot.services.SpaPriceService;
import com.camper.yantarniytelegrambot.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public abstract class AbstractSpaServiceButtonHandler implements BotButtonHandler {
    protected LocaleMessageSource localeMessageSource;
    protected SpaPriceService spaPriceService;
    protected List<SpaPrice> services = null;
    protected int currentPage = 1;

    @Override
    public List<PartialBotApiMethod<?>> handle(String chatId, CallbackQuery query) {
        services = spaPriceService.findAll().stream().filter(s -> s.getLocation().getTitle().equals(getLocationName())).collect(Collectors.toList());
        currentPage = 1;

        List<PartialBotApiMethod<?>> answers = new ArrayList<>();

        if (!services.isEmpty()) {
            SpaPrice selectedService = services.get(0);
            byte[] image = selectedService.getImage();
            if (image != null) {
                SendPhoto.SendPhotoBuilder builder = SendPhoto.builder();
                builder.chatId(chatId);
                builder.photo(new InputFile(new ByteArrayInputStream(image), "filename"));
                builder.replyMarkup(getPriceScrollMarkup(services.size()));

                answers.add(builder.build());
            }
            answers.add(Utils.deleteMessage(chatId, query.getMessage().getMessageId()));
        }
        return answers;
    }

    public List<PartialBotApiMethod<?>> scrollPrice(String chatId, CallbackQuery query,ScrollState scrollState) {
        if (services == null) {
            services = spaPriceService.findAll().stream().filter(s -> s.getLocation().getTitle().equals(getLocationName())).collect(Collectors.toList());
        }
        if (scrollState.equals(ScrollState.NEXT)) {
            if (services.isEmpty() || currentPage == services.size()) {
                return null;
            }
            currentPage++;
        } else {
            if (services.isEmpty() || currentPage == 1) {
                return null;
            }
            currentPage--;
        }

        return new ArrayList<>(Utils.scrollMenuItem(chatId,
                query.getMessage(),
                query,
                getPriceScrollMarkup(services.size()),
                services.get(currentPage - 1).getImage(),
                null));
    }

    protected abstract String getLocationName();
    protected abstract InlineKeyboardMarkup getPriceScrollMarkup(int numberOfPrices);

    @Autowired
    public void setLocaleMessageSource(LocaleMessageSource localeMessageSource) {
        this.localeMessageSource = localeMessageSource;
    }

    @Autowired
    public void setSpaServService(SpaPriceService spaPriceService) {
        this.spaPriceService = spaPriceService;
    }
}

package com.camper.yantarniytelegrambot.handlers.Spa;

import com.camper.yantarniytelegrambot.entity.Employee;
import com.camper.yantarniytelegrambot.entity.Location;
import com.camper.yantarniytelegrambot.entity.Sale;
import com.camper.yantarniytelegrambot.enums.ScrollState;
import com.camper.yantarniytelegrambot.handlers.BotButtonHandler;
import com.camper.yantarniytelegrambot.services.LocaleMessageSource;
import com.camper.yantarniytelegrambot.services.LocationService;
import com.camper.yantarniytelegrambot.services.SaleService;
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
import java.util.Collections;
import java.util.List;

@Component
public class SpaSalesButtonHandler implements BotButtonHandler {
    private LocaleMessageSource localeMessageSource;
    private SaleService saleService;
    private int currentPage = 1;
    private List<Sale> sales = null;
    private Location location;

    @Override
    public List<PartialBotApiMethod<?>> handle(String chatId, CallbackQuery query) {
        sales = saleService.findAllByLocation(location);
        currentPage = 1;

        List<PartialBotApiMethod<?>> answers = new ArrayList<>();
        if (sales != null && !sales.isEmpty()) {
            Sale selectedSale = sales.get(0);

            if (selectedSale.getImage() != null) {
                SendPhoto.SendPhotoBuilder builder = SendPhoto.builder();
                builder.chatId(chatId);
                builder.photo(new InputFile(new ByteArrayInputStream(selectedSale.getImage()), "filename"));
                builder.replyMarkup(getSpaCardSalesMarkup(sales.size()));

                String description = selectedSale.getDescription();
                if (description != null) {
                    builder.caption(description);
                }

                SendPhoto sendPhoto = builder.build();
                answers.add(sendPhoto);
            } else {
                SendMessage sendMessage = new SendMessage(chatId, selectedSale.getDescription());
                sendMessage.setReplyMarkup(getSpaCardSalesMarkup(sales.size()));
                answers.add(sendMessage);
            }
        } else {
            SendMessage sendMessage = new SendMessage(chatId, localeMessageSource.getMessage("onAction.spaSalesButton"));
            sendMessage.setReplyMarkup(BotButtonHandler.getReturnMarkup("handleSpaButton"));
            answers.add(sendMessage);
        }

        answers.add(Utils.deleteMessage(chatId, query.getMessage().getMessageId()));

        return answers;
    }

    public List<PartialBotApiMethod<?>> scrollItem(String chatId, CallbackQuery query, ScrollState scrollState) {
        if (sales == null) {
            sales = saleService.findAllByLocation(location);
        }
        if (scrollState.equals(ScrollState.NEXT)) {
            if (sales.isEmpty() || currentPage == sales.size()) {
                return null;
            }
            currentPage++;
        } else {
            if (sales.isEmpty() || currentPage == 1) {
                return null;
            }
            currentPage--;
        }
        Sale selectedSale = sales.get(currentPage - 1);

        return new ArrayList<>(Utils.scrollMenuItem(chatId,
                query.getMessage(),
                query,
                getSpaCardSalesMarkup(sales.size()),
                selectedSale.getImage(),
                selectedSale.getDescription()));
    }

    private InlineKeyboardMarkup getSpaCardSalesMarkup(int numberOfSales) {
        return BotButtonHandler.getScrollMenuMarkup(numberOfSales, currentPage,
                "handleSpaSalesPrevButton",
                "handleSpaSalesNextButton",
                "handleSpaSpecialistsButton",
                null,
                null);
    }

    @Autowired
    public void setLocaleMessageSource(LocaleMessageSource localeMessageSource) {
        this.localeMessageSource = localeMessageSource;
    }

    @Autowired
    public void setSaleService(SaleService saleService) {
        this.saleService = saleService;
    }

    @Autowired
    public void setLocationService(LocationService locationService) {
        location = locationService.findLocationByTitle("SPA");
    }
}

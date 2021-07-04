package com.camper.yantarniytelegrambot.handlers.ClubCards;

import com.camper.yantarniytelegrambot.botapi.YantarniyTelegramBot;
import com.camper.yantarniytelegrambot.entity.Location;
import com.camper.yantarniytelegrambot.entity.Sale;
import com.camper.yantarniytelegrambot.handlers.BotButtonHandler;
import com.camper.yantarniytelegrambot.services.LocaleMessageSource;
import com.camper.yantarniytelegrambot.services.LocationService;
import com.camper.yantarniytelegrambot.services.SaleService;
import com.camper.yantarniytelegrambot.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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
@Scope("singleton")
public class ClubCardSalesButtonHandler implements BotButtonHandler {
    private LocaleMessageSource localeMessageSource;
    private SaleService saleService;
    private LocationService locationService;
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
                builder.replyMarkup(getClubCardSalesMarkup(sales.size()));

                String description = selectedSale.getDescription();
                if (description != null) {
                    builder.caption(description);
                }

                SendPhoto sendPhoto = builder.build();
                answers.add(sendPhoto);
            } else {
                SendMessage sendMessage = new SendMessage(chatId, selectedSale.getDescription());
                sendMessage.setReplyMarkup(getClubCardSalesMarkup(sales.size()));
                answers.add(sendMessage);
            }
        } else {
            SendMessage sendMessage = new SendMessage(chatId, localeMessageSource.getMessage("onAction.clubCardsSalesButton"));
            sendMessage.setReplyMarkup(getClubCardSalesMarkup(sales.size()));
            answers.add(sendMessage);
        }

        answers.add(Utils.deleteMessage(chatId, query.getMessage().getMessageId()));

        return answers;
    }

    public List<PartialBotApiMethod<?>> nextSale(String chatId, CallbackQuery query) {
        if (sales == null) {
            sales = saleService.findAllByLocation(location);
        }
        if (sales.isEmpty() || currentPage == sales.size()) {
            return null;
        }
        currentPage++;
        Sale selectedSale = sales.get(currentPage - 1);
        Integer messageId = query.getMessage().getMessageId();

        return new ArrayList<>(Utils.scrollMenuItem(chatId
                , messageId
                , query
                , getClubCardSalesMarkup(sales.size())
                , selectedSale.getImage()
                , selectedSale.getDescription()));
    }

    public List<PartialBotApiMethod<?>> previousSale(String chatId, CallbackQuery query) {
        if (sales == null) {
            sales = saleService.findAllByLocation(location);
        }
        if (sales.isEmpty() || currentPage == 1) {
            return null;
        }
        currentPage--;
        Sale selectedSale = sales.get(currentPage - 1);
        Integer messageId = query.getMessage().getMessageId();

        return new ArrayList<>(Utils.scrollMenuItem(chatId
                , messageId
                , query
                , getClubCardSalesMarkup(sales.size())
                , selectedSale.getImage()
                , selectedSale.getDescription()));
    }

    public List<PartialBotApiMethod<?>> returnToMainMenu(String chatId, CallbackQuery query) {
        currentPage = 1;
        sales = null;
        return Utils.moveToMainMenu(chatId,query.getMessage().getMessageId());
    }

    private InlineKeyboardMarkup getClubCardSalesMarkup(int numberOfSales) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton prevButton = new InlineKeyboardButton("<--");
        InlineKeyboardButton countButton = new InlineKeyboardButton((numberOfSales == 0 ? 0 : currentPage) + "/" + numberOfSales);
        InlineKeyboardButton nextButton = new InlineKeyboardButton("-->");
        InlineKeyboardButton mainMenuButton = new InlineKeyboardButton(localeMessageSource.getMessage("other.mainMenu"));

        prevButton.setCallbackData("handleClubCardsSalesPrevButton");
        nextButton.setCallbackData("handleClubCardsSalesNextButton");
        countButton.setCallbackData("null");
        mainMenuButton.setCallbackData("handleClubCardsSalesReturnButton");

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(prevButton);
        firstRow.add(countButton);
        firstRow.add(nextButton);

        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        secondRow.add(mainMenuButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(Arrays.asList(firstRow, secondRow));

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
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
        this.locationService = locationService;
        location = locationService.findLocationByTitle("CLUB_CARDS");
    }
}

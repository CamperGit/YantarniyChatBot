package com.camper.yantarniytelegrambot.handlers.Sales;

import com.camper.yantarniytelegrambot.handlers.BotButtonHandler;
import com.camper.yantarniytelegrambot.services.LocaleMessageSource;
import com.camper.yantarniytelegrambot.utils.Utils;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SalesMenuButtonHandler implements BotButtonHandler {
    private LocaleMessageSource localeMessageSource;
    @Getter
    private ClubCardSalesButtonHandler clubCardSalesButtonHandler;
    @Getter
    private CCSalesContactUsButtonHandler ccSalesContactUsButtonHandler;
    @Getter
    private SpaSalesButtonHandler spaSalesButtonHandler;
    @Getter
    private SpaSalesContactUsButtonHandler spaSalesContactUsButtonHandler;

    @Override
    public List<PartialBotApiMethod<?>> handle(String chatId, CallbackQuery query) {
        return new ArrayList<>(Arrays.asList(Utils.deleteMessage(chatId, query.getMessage().getMessageId()),
                SendMessage.builder()
                        .chatId(chatId)
                        .text(localeMessageSource.getMessage("onAction.salesButton"))
                        .replyMarkup(getSalesMenuMarkup())
                        .build()));
    }

    private InlineKeyboardMarkup getSalesMenuMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton clubCardSalesButton = new InlineKeyboardButton(localeMessageSource.getMessage("clubCardMenu.salesButton"));
        InlineKeyboardButton spaSalesButton = new InlineKeyboardButton(localeMessageSource.getMessage("spa.sales"));
        InlineKeyboardButton returnButton = new InlineKeyboardButton(localeMessageSource.getMessage("other.moveBack"));

        clubCardSalesButton.setCallbackData("handleClubCardsSalesButton");
        spaSalesButton.setCallbackData("handleSpaSalesButton");
        returnButton.setCallbackData("handleReturnMainMenuButton");

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(clubCardSalesButton);
        firstRow.add(spaSalesButton);

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
    public void setClubCardSalesButtonHandler(ClubCardSalesButtonHandler clubCardSalesButtonHandler) {
        this.clubCardSalesButtonHandler = clubCardSalesButtonHandler;
    }

    @Autowired
    public void setCcSalesContactUsButtonHandler(CCSalesContactUsButtonHandler ccSalesContactUsButtonHandler) {
        this.ccSalesContactUsButtonHandler = ccSalesContactUsButtonHandler;
    }

    @Autowired
    public void setSpaSalesButtonHandler(SpaSalesButtonHandler spaSalesButtonHandler) {
        this.spaSalesButtonHandler = spaSalesButtonHandler;
    }

    @Autowired
    public void setSpaSalesContactUsButtonHandler(SpaSalesContactUsButtonHandler spaSalesContactUsButtonHandler) {
        this.spaSalesContactUsButtonHandler = spaSalesContactUsButtonHandler;
    }
}

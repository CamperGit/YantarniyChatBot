package com.camper.yantarniytelegrambot.handlers.ClubCards;

import com.camper.yantarniytelegrambot.botapi.YantarniyTelegramBot;
import com.camper.yantarniytelegrambot.entity.CardType;
import com.camper.yantarniytelegrambot.entity.Location;
import com.camper.yantarniytelegrambot.entity.Sale;
import com.camper.yantarniytelegrambot.handlers.BotButtonHandler;
import com.camper.yantarniytelegrambot.services.CardTypeService;
import com.camper.yantarniytelegrambot.services.LocaleMessageSource;
import com.camper.yantarniytelegrambot.services.LocationService;
import com.camper.yantarniytelegrambot.services.SaleService;
import com.camper.yantarniytelegrambot.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class ClubCardTypeButtonHandler implements BotButtonHandler {
    private CardTypeService cardTypeService;
    private LocaleMessageSource localeMessageSource;

    @Override
    public List<PartialBotApiMethod<?>> handle(String chatId, CallbackQuery query) {
        StringBuilder messageBuilder = new StringBuilder();

        messageBuilder.append(localeMessageSource.getMessage("onAction.clubCardsTypesButton")).append("\n");

        List<CardType> cards = cardTypeService.findAll();
        for (CardType cardType : cards) {
            messageBuilder.append(cardType.getTitle());
            if (cardType.getDescription() != null) {
                messageBuilder.append("Описание: ");
                messageBuilder.append(cardType.getDescription());
            }
            messageBuilder.append("\n\n");
        }

        return new ArrayList<>(Collections.singletonList(Utils.changeMessage(messageBuilder.toString(),chatId,query.getMessage().getMessageId(),getCardTypesMarkup())));
    }

    @Autowired
    public void setLocaleMessageSource(LocaleMessageSource localeMessageSource) {
        this.localeMessageSource = localeMessageSource;
    }

    @Autowired
    public void setCardTypeService(CardTypeService cardTypeService) {
        this.cardTypeService = cardTypeService;
    }

    public InlineKeyboardMarkup getCardTypesMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton exitButton = new InlineKeyboardButton(localeMessageSource.getMessage("other.mainMenu"));
        exitButton.setCallbackData("handleReturnMainMenuButton");
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(exitButton);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(Collections.singletonList(firstRow));
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }
}

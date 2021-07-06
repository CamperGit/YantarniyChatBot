package com.camper.yantarniytelegrambot.handlers.Spa;

import com.camper.yantarniytelegrambot.handlers.BotButtonHandler;
import com.camper.yantarniytelegrambot.handlers.Spa.Specialists.*;
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
public class SpaButtonHandler implements BotButtonHandler {
    private LocaleMessageSource localeMessageSource;
    @Getter
    private SpaSalesButtonHandler spaSalesButtonHandler;
    @Getter
    private SpaNailsButtonHandler spaNailsButtonHandler;
    @Getter
    private SpaMassageButtonHandler spaMassageButtonHandler;
    @Getter
    private SpaCosmetologyButtonHandler spaCosmetologyButtonHandler;
    @Getter
    private SpaStylistsButtonHandler spaStylistsButtonHandler;
    @Getter
    private SpaContactUsButtonHandler spaContactUsButtonHandler;

    @Override
    public List<PartialBotApiMethod<?>> handle(String chatId, CallbackQuery query) {
        return new ArrayList<>(Arrays.asList(Utils.deleteMessage(chatId,query.getMessage().getMessageId()),
                SendMessage.builder()
                        .chatId(chatId)
                        .text(localeMessageSource.getMessage("onAction.spaButton"))
                        .replyMarkup(getSpaMenuMarkup())
                        .build()));
    }

    public List<PartialBotApiMethod<?>> openSpecialistsMenu(String chatId, CallbackQuery query) {
        return new ArrayList<>(Arrays.asList(Utils.deleteMessage(chatId,query.getMessage().getMessageId()),
                SendMessage.builder()
                        .chatId(chatId)
                        .text(localeMessageSource.getMessage("onAction.spaSpecialistsButton"))
                        .replyMarkup(getSpaSpecialistsMarkup())
                        .build()));

    }

    private InlineKeyboardMarkup getSpaMenuMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton specialistsButton = new InlineKeyboardButton(localeMessageSource.getMessage("spa.specialists"));
        InlineKeyboardButton spaSalesButton = new InlineKeyboardButton(localeMessageSource.getMessage("spa.sales"));
        InlineKeyboardButton exitButton = new InlineKeyboardButton(localeMessageSource.getMessage("other.moveBack"));

        specialistsButton.setCallbackData("handleSpaSpecialistsButton");
        spaSalesButton.setCallbackData("handleSpaSalesButton");
        exitButton.setCallbackData("handleReturnMainMenuButton");

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(specialistsButton);
        firstRow.add(spaSalesButton);

        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        secondRow.add(exitButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(Arrays.asList(firstRow, secondRow));

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    private InlineKeyboardMarkup getSpaSpecialistsMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton nailsButton = new InlineKeyboardButton(localeMessageSource.getMessage("spa.specialists.nails"));
        InlineKeyboardButton massageButton = new InlineKeyboardButton(localeMessageSource.getMessage("spa.specialists.massage"));
        InlineKeyboardButton cosmetologyButton = new InlineKeyboardButton(localeMessageSource.getMessage("spa.specialists.cosmetology"));
        InlineKeyboardButton stylistsButton = new InlineKeyboardButton(localeMessageSource.getMessage("spa.specialists.stylists"));
        InlineKeyboardButton returnButton = new InlineKeyboardButton(localeMessageSource.getMessage("other.moveBack"));

        nailsButton.setCallbackData("handleSpaNailsButton");
        massageButton.setCallbackData("handleSpaMassageButton");
        cosmetologyButton.setCallbackData("handleSpaCosmetologyButton");
        stylistsButton.setCallbackData("handleSpaStylistsButton");
        returnButton.setCallbackData("handleSpaButton");

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(nailsButton);
        firstRow.add(massageButton);

        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        secondRow.add(cosmetologyButton);
        secondRow.add(stylistsButton);

        List<InlineKeyboardButton> thirdRow = new ArrayList<>();
        thirdRow.add(returnButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(Arrays.asList(firstRow, secondRow,thirdRow));

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    @Autowired
    public void setSpaSalesButtonHandler(SpaSalesButtonHandler spaSalesButtonHandler) {
        this.spaSalesButtonHandler = spaSalesButtonHandler;
    }

    @Autowired
    public void setLocaleMessageSource(LocaleMessageSource localeMessageSource) {
        this.localeMessageSource = localeMessageSource;
    }

    @Autowired
    public void setSpaNailsButtonHandler(SpaNailsButtonHandler spaNailsButtonHandler) {
        this.spaNailsButtonHandler = spaNailsButtonHandler;
    }

    @Autowired
    public void setSpaMassageButtonHandler(SpaMassageButtonHandler spaMassageButtonHandler) {
        this.spaMassageButtonHandler = spaMassageButtonHandler;
    }

    @Autowired
    public void setSpaCosmetologyButtonHandler(SpaCosmetologyButtonHandler spaCosmetologyButtonHandler) {
        this.spaCosmetologyButtonHandler = spaCosmetologyButtonHandler;
    }

    @Autowired
    public void setSpaStylistsButtonHandler(SpaStylistsButtonHandler spaStylistsButtonHandler) {
        this.spaStylistsButtonHandler = spaStylistsButtonHandler;
    }

    @Autowired
    public void setSpaContactUsButtonHandler(SpaContactUsButtonHandler spaContactUsButtonHandler) {
        this.spaContactUsButtonHandler = spaContactUsButtonHandler;
    }
}

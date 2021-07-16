package com.camper.yantarniytelegrambot.handlers.SpaService;

import com.camper.yantarniytelegrambot.handlers.BotButtonHandler;
import com.camper.yantarniytelegrambot.handlers.SpaService.Services.*;
import com.camper.yantarniytelegrambot.services.LocaleMessageSource;
import com.camper.yantarniytelegrambot.utils.Utils;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SpaServiceMenuButtonHandler implements BotButtonHandler {
    private LocaleMessageSource localeMessageSource;
    @Getter
    private NailsSSButtonHandler nailsSSButtonHandler;
    @Getter
    private CosmetologySSButtonHandler cosmetologySSButtonHandler;
    @Getter
    private StylistsSSButtonHandler stylistsSSButtonHandler;
    @Getter
    private MassageSSButtonHandler massageSSButtonHandler;
    @Getter
    private BathhouseSSButtonHandler bathhouseSSButtonHandler;

    @Override
    public List<PartialBotApiMethod<?>> handle(String chatId, CallbackQuery query) {
        return new ArrayList<>(Utils.changeMessage(localeMessageSource.getMessage("onAction.spaService"),
                chatId,
                query.getMessage(),
                getSpaServicesMenuMarkup()));
    }

    private InlineKeyboardMarkup getSpaServicesMenuMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton nailsButton = new InlineKeyboardButton(localeMessageSource.getMessage("spaServices.nails"));
        InlineKeyboardButton cosmetologyButton = new InlineKeyboardButton(localeMessageSource.getMessage("spaServices.cosmetology"));
        InlineKeyboardButton stylistsButton = new InlineKeyboardButton(localeMessageSource.getMessage("spaServices.stylists"));
        InlineKeyboardButton massageButton = new InlineKeyboardButton(localeMessageSource.getMessage("spaServices.massage"));
        InlineKeyboardButton bathhouseButton = new InlineKeyboardButton(localeMessageSource.getMessage("spaServices.bathhouse"));
        InlineKeyboardButton returnButton = new InlineKeyboardButton(localeMessageSource.getMessage("other.moveBack"));

        nailsButton.setCallbackData("handleSSNailsButton");
        cosmetologyButton.setCallbackData("handleSSCosmetologyButton");
        stylistsButton.setCallbackData("handleSSStylistsButton");
        massageButton.setCallbackData("handleSSMassageButton");
        bathhouseButton.setCallbackData("handleSSBathhouseButton");
        returnButton.setCallbackData("handleReturnMainMenuButton");

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(nailsButton);
        firstRow.add(nailsButton);
        firstRow.add(cosmetologyButton);

        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        secondRow.add(stylistsButton);
        secondRow.add(massageButton);
        secondRow.add(bathhouseButton);

        List<InlineKeyboardButton> thirdRow = new ArrayList<>();
        thirdRow.add(returnButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(Arrays.asList(firstRow, secondRow, thirdRow));

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    @Autowired
    public void setLocaleMessageSource(LocaleMessageSource localeMessageSource) {
        this.localeMessageSource = localeMessageSource;
    }

    @Autowired
    public void setNailsSSButtonHandler(NailsSSButtonHandler nailsSSButtonHandler) {
        this.nailsSSButtonHandler = nailsSSButtonHandler;
    }

    @Autowired
    public void setCosmetologySSButtonHandler(CosmetologySSButtonHandler cosmetologySSButtonHandler) {
        this.cosmetologySSButtonHandler = cosmetologySSButtonHandler;
    }

    @Autowired
    public void setStylistsSSButtonHandler(StylistsSSButtonHandler stylistsSSButtonHandler) {
        this.stylistsSSButtonHandler = stylistsSSButtonHandler;
    }

    @Autowired
    public void setMassageSSButtonHandler(MassageSSButtonHandler massageSSButtonHandler) {
        this.massageSSButtonHandler = massageSSButtonHandler;
    }

    @Autowired
    public void setBathhouseSSButtonHandler(BathhouseSSButtonHandler bathhouseSSButtonHandler) {
        this.bathhouseSSButtonHandler = bathhouseSSButtonHandler;
    }
}

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
        return new ArrayList<>(Utils.changeMessage(localeMessageSource.getMessage("onAction.clubCardsTypesButton"),
                chatId,
                query.getMessage(),
                getCardTypesMarkup()));
    }

    public List<PartialBotApiMethod<?>> individualGoldCard(String chatId, CallbackQuery query) {
        String message = "<b><u>Индивидуальная GOLD</u></b>\n" +
                "1 месяц.\n" +
                "3 месяца.\n" +
                "6 месяцев.\n" +
                "12 месяцев.\n" +
                "\n<b>Время посещения\n</b>" +
                "<i>Рабочие дни:</i> с 7:00 до 23:00\n" +
                "<i>Выходные и праздничные дни:</i> с 9:00 до 23:00\n" +
                "\n<b>В клубную карту включено:</b>\n" +
                "1. Тренажерный зал\n" +
                "2. Зал групповых программ\n" +
                "3. Бассейн\n" +
                "4. Финская сауна\n" +
                "5. Турецкий хамам\n" +
                "6. Фитнес диагностика\n" +
                "7. Велнес консультация\n" +
                "8. Гостевой визит\n" +
                "9. Заморозка клубной карты\n" +
                "\n<b>Связаться с менеджером</b>: 2020302\n";
        return new ArrayList<>(Utils.changeMessage(message,
                chatId,
                query.getMessage(),
                BotButtonHandler.getReturnMarkup("handleClubCardsTypesButton")));
    }

    public List<PartialBotApiMethod<?>> dayGoldCard(String chatId, CallbackQuery query) {
        String message = "<b><u>Дневная GOLD</u></b>\n" +
                "6 месяцев.\n" +
                "12 месяцев.\n" +
                "\n<b>Время посещения\n</b>" +
                "<i>Рабочие дни:</i> с 7:00 до 16:00\n" +
                "<i>Выходные и праздничные дни:</i> с 9:00 до 16:00\n" +
                "\n<b>В клубную карту включено:</b>\n" +
                "1. Тренажерный зал\n" +
                "2. Зал групповых программ\n" +
                "3. Бассейн\n" +
                "4. Финская сауна\n" +
                "5. Турецкий хамам\n" +
                "6. Фитнес диагностика\n" +
                "7. Велнес консультация\n" +
                "8. Гостевой визит\n" +
                "9. Заморозка клубной карты\n" +
                "\n<b>Связаться с менеджером</b>: 2020302\n";
        return new ArrayList<>(Utils.changeMessage(message,
                chatId,
                query.getMessage(),
                BotButtonHandler.getReturnMarkup("handleClubCardsTypesButton")));
    }

    public List<PartialBotApiMethod<?>> weekendCard(String chatId, CallbackQuery query) {
        String message = "<b><u>Карта выходного дня</u></b>\n" +
                "12 месяцев.\n" +
                "\n<b>Время посещения\n</b>" +
                "<i>Выходные и праздничные дни:</i> с 9:00 до 23:00\n" +
                "\n<b>В клубную карту включено:</b>\n" +
                "1. Тренажерный зал\n" +
                "2. Зал групповых программ\n" +
                "3. Бассейн\n" +
                "4. Финская сауна\n" +
                "5. Турецкий хамам\n" +
                "6. Фитнес диагностика\n" +
                "7. Велнес консультация\n" +
                "8. Гостевой визит\n" +
                "9. Заморозка клубной карты\n" +
                "\n<b>Связаться с менеджером</b>: 2020302\n";

        return new ArrayList<>(Utils.changeMessage(message,
                chatId,
                query.getMessage(),
                BotButtonHandler.getReturnMarkup("handleClubCardsTypesButton")));
    }

    public List<PartialBotApiMethod<?>> poolCard(String chatId, CallbackQuery query) {
        String message = "<b><u>Бассейн</u></b>\n" +
                "6 месяцев.\n" +
                "\n<b>Время посещения\n</b>" +
                "<i>Рабочие дни:</i> с 7:00 до 17:00 и с 20:00 до 23:00\n" +
                "<i>Выходные и праздничные дни:</i> с 16:00 до 23:00\n" +
                "\n<b>В клубную карту включено:</b>\n" +
                "1. Бассейн\n" +
                "2. Финская сауна\n" +
                "3. Турецкий хамам\n" +
                "4. Фитнес диагностика\n" +
                "5. Велнес консультация\n" +
                "6. Гостевой визит\n" +
                "7. Заморозка клубной карты\n" +
                "\n<b>Связаться с менеджером</b>: 2020302\n";
        return new ArrayList<>(Utils.changeMessage(message,
                chatId,
                query.getMessage(),
                BotButtonHandler.getReturnMarkup("handleClubCardsTypesButton")));
    }

    private InlineKeyboardMarkup getCardTypesMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton individualGoldButton = new InlineKeyboardButton(localeMessageSource.getMessage("clubCard.type.individualGold"));
        InlineKeyboardButton dayGoldButton = new InlineKeyboardButton(localeMessageSource.getMessage("clubCard.type.dayGold"));
        InlineKeyboardButton weekendButton = new InlineKeyboardButton(localeMessageSource.getMessage("clubCard.type.weekend"));
        InlineKeyboardButton poolButton = new InlineKeyboardButton(localeMessageSource.getMessage("clubCard.type.pool"));
        InlineKeyboardButton returnButton = new InlineKeyboardButton(localeMessageSource.getMessage("other.moveBack"));

        individualGoldButton.setCallbackData("handleIndGoldClubCardButton");
        dayGoldButton.setCallbackData("handleDayGoldClubCardButton");
        weekendButton.setCallbackData("handleWeekendClubCardButton");
        poolButton.setCallbackData("handlePoolClubCardButton");
        returnButton.setCallbackData("handleReturnMainMenuButton");

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(individualGoldButton);
        firstRow.add(dayGoldButton);

        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        secondRow.add(weekendButton);
        secondRow.add(poolButton);

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
    public void setCardTypeService(CardTypeService cardTypeService) {
        this.cardTypeService = cardTypeService;
    }
}

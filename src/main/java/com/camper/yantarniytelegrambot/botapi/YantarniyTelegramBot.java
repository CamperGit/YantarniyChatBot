package com.camper.yantarniytelegrambot.botapi;

import com.camper.yantarniytelegrambot.entity.*;
import com.camper.yantarniytelegrambot.enums.ScheduleType;
import com.camper.yantarniytelegrambot.handlers.BotActionListener;
import com.camper.yantarniytelegrambot.services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.groupadministration.SetChatPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
@SuppressWarnings("unchecked")
public class YantarniyTelegramBot extends TelegramWebhookBot {
    private static final Map<String, Method> handlers;
    private final String WEB_HOOK_PATH;
    private final String USERNAME;
    private final String TOKEN;
    private BotActionListener botActionListener;
    private LocaleMessageSource localeMessageSource;
    private SaleService saleService;
    private LocationService locationService;
    private EmployeeTypeService employeeTypeService;
    private EmployeeService employeeService;

    static {
        handlers = new HashMap<>();
        for (Method m : BotActionListener.class.getDeclaredMethods()) {
            handlers.put(m.getName(),m);
        }
    }

    public YantarniyTelegramBot(DefaultBotOptions options,String WEB_HOOK_PATH, String USERNAME, String TOKEN) {
        super(options);
        this.WEB_HOOK_PATH = WEB_HOOK_PATH;
        this.USERNAME = USERNAME;
        this.TOKEN = TOKEN;
    }

    @Override
    public String getBotUsername() {
        return USERNAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    @Override
    public String getBotPath() {
        return WEB_HOOK_PATH;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {

        //CallbackQuery handling
        if (update.hasCallbackQuery() && !update.getCallbackQuery().getData().equals("null")) {
            CallbackQuery query = update.getCallbackQuery();
            String chatId = query.getMessage().getChatId().toString();
            String data = update.getCallbackQuery().getData();
            Method handler = handlers.get(data);
            try {
                if (handler != null) {
                    List<PartialBotApiMethod<?>> answers = (List<PartialBotApiMethod<?>>) handler.invoke(botActionListener,chatId,query);
                    if (answers != null) {
                        for (PartialBotApiMethod<?> answer : answers) {
                            if (answer instanceof BotApiMethod<?>) {
                                execute((BotApiMethod<? extends Serializable>) answer);
                            } else if (answer instanceof EditMessageMedia) {
                                execute((EditMessageMedia) answer);
                            } else if (answer instanceof SendPhoto) {
                                execute((SendPhoto) answer);
                            }
                        }
                    }
                } else {
                    log.warn("Not found handler for selected button: \"" + query.getMessage().getText() + "\", and callback query value = " + query.getData());
                }
            } catch (IllegalAccessException | InvocationTargetException | TelegramApiException e) {
                e.printStackTrace();
            }
        }

        //Commands and nonCommands messages handling
        if (update.getMessage() != null && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();
            switch (text) {
                case "/start": {
                    return createMainMenuMessage(chatId,localeMessageSource.getMessage("mainMenu.menuLabel"));
                }
                case "/test" : {

                    /*Location location = locationService.findLocationByTitle("SPA");
                    Sale sale2 = new Sale(null,"❗Внимание акция! Только 3 дня❗\n" +
                            "\n" +
                            "Биоревитализация препаратами Meso-Xanthin, Meso-wharton, Mesoeye, по специальной цене \n" +
                            "\n" +
                            "\uD83D\uDD259900 руб., вместо 14 500 руб. \n" +
                            "\n" +
                            "Преобрести можно сейчас, а использовать в течении 60 дней. \n" +
                            "\n" +
                            "Подробности по телефону 202-07-02",location);
                    saleService.putIfAbsent(sale2);*/
                    break;
                }
                default : {
                    return createMainMenuMessage(chatId,localeMessageSource.getMessage("other.unknownNonCommandMessage"));
                }
            }
        }
        return null;
    }

    public static SendMessage createMainMenuMessage(String chatId, String text) {
        SendMessage sendMessage = new SendMessage(chatId, text);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton clubCartsButton = new InlineKeyboardButton("Клубные карты");
        InlineKeyboardButton fitnessButton = new InlineKeyboardButton("Фитнес");
        InlineKeyboardButton spaButton = new InlineKeyboardButton("Спа");
        InlineKeyboardButton contactUsButton = new InlineKeyboardButton("Связаться с менеджером");

        clubCartsButton.setCallbackData("handleClubCardButton");
        fitnessButton.setCallbackData("handleFitnessButton");
        spaButton.setCallbackData("handleSpaButton");
        contactUsButton.setCallbackData("contactUs");

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(clubCartsButton);
        firstRow.add(fitnessButton);

        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        secondRow.add(spaButton);
        secondRow.add(contactUsButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(Arrays.asList(firstRow, secondRow));

        inlineKeyboardMarkup.setKeyboard(rowList);

        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }

    @Autowired
    public void setLocaleMessageSource(LocaleMessageSource localeMessageSource) {
        this.localeMessageSource = localeMessageSource;
    }

    @Autowired
    public void setBotActionListener(BotActionListener botActionListener) {
        this.botActionListener = botActionListener;
    }

    @Autowired
    public void setSaleService(SaleService saleService) {
        this.saleService = saleService;
    }

    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    @Autowired
    public void setEmployeeTypeService(EmployeeTypeService employeeTypeService) {
        this.employeeTypeService = employeeTypeService;
    }

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
}

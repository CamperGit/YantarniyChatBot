package com.camper.yantarniytelegrambot.botapi;

import com.camper.yantarniytelegrambot.handlers.BotActionListener;
import com.camper.yantarniytelegrambot.services.LocaleMessageSource;
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
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
                default : {
                    return createMainMenuMessage(chatId,localeMessageSource.getMessage("other.unknownNonCommandMessage"));
                }
            }
        }
        return null;
    }


    private InlineKeyboardMarkup getMainMenuMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton clubCartsButton = new InlineKeyboardButton(localeMessageSource.getMessage("mainMenu.clubCardsButton"));
        InlineKeyboardButton fitnessButton = new InlineKeyboardButton(localeMessageSource.getMessage("mainMenu.fitnessButton"));
        InlineKeyboardButton spaButton = new InlineKeyboardButton(localeMessageSource.getMessage("mainMenu.spaButton"));
        InlineKeyboardButton contactUsButton = new InlineKeyboardButton(localeMessageSource.getMessage("mainMenu.contactUsButton"));

        clubCartsButton.setCallbackData("handleClubCardButton");
        fitnessButton.setCallbackData("fitnes");
        spaButton.setCallbackData("spa");
        contactUsButton.setCallbackData("contactUs");

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(clubCartsButton);
        firstRow.add(fitnessButton);

        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        secondRow.add(spaButton);
        secondRow.add(contactUsButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(Arrays.asList(firstRow, secondRow));

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
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
        spaButton.setCallbackData("spa");
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
}

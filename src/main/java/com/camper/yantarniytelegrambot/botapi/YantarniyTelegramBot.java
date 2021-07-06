package com.camper.yantarniytelegrambot.botapi;

import com.camper.yantarniytelegrambot.entity.*;
import com.camper.yantarniytelegrambot.enums.ScheduleType;
import com.camper.yantarniytelegrambot.enums.UserRole;
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
import org.telegram.telegrambots.meta.api.objects.User;
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
    private UserEntityService userEntityService;
    private SaleService saleService;

    static {
        handlers = new HashMap<>();
        for (Method m : BotActionListener.class.getDeclaredMethods()) {
            handlers.put(m.getName(), m);
        }
    }

    public YantarniyTelegramBot(DefaultBotOptions options, String WEB_HOOK_PATH, String USERNAME, String TOKEN) {
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
                    List<PartialBotApiMethod<?>> answers = (List<PartialBotApiMethod<?>>) handler.invoke(botActionListener, chatId, query);
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
                    User user = update.getMessage().getFrom();
                    String firstName = user.getFirstName();
                    String lastName = user.getLastName();
                    String username = user.getUserName();
                    UserEntity newUser = new UserEntity(chatId, firstName, lastName, username, null, UserRole.USER);
                    userEntityService.putIfAbsent(newUser);
                    return createMainMenuMessage(chatId, localeMessageSource.getMessage("mainMenu.menuLabel"));
                }
                case "/auto": {
                    /*List<Sale> sales = saleService.findAll();
                    List<UserEntity> users = userEntityService.findAll();
                    for (UserEntity user : users) {
                        if (user.getRole().equals(UserRole.ADMIN)) {
                            continue;
                        }
                        for (Sale sale : sales) {
                            if (sale.getImage() != null) {
                                SendPhoto.SendPhotoBuilder builder = SendPhoto.builder();
                                builder.chatId(user.getChatId());
                                builder.photo(new InputFile(new ByteArrayInputStream(sale.getImage()), "filename"));
                                try {
                                    execute(builder.build());
                                } catch (TelegramApiException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }*/
                    break;
                }
                case "/test": {
                    break;
                }
                default: {
                    return createMainMenuMessage(chatId, localeMessageSource.getMessage("other.unknownNonCommandMessage"));
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
        InlineKeyboardButton spaButton = new InlineKeyboardButton("СПА");
        InlineKeyboardButton contactUsButton = new InlineKeyboardButton("Связаться с менеджером");
        InlineKeyboardButton sberQrButton = new InlineKeyboardButton("Плати QR от Сбера");

        clubCartsButton.setCallbackData("handleClubCardButton");
        fitnessButton.setCallbackData("handleFitnessButton");
        spaButton.setCallbackData("handleSpaButton");
        contactUsButton.setCallbackData("handleContactUsButton");
        sberQrButton.setCallbackData("handleQrSberButton");

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(clubCartsButton);
        firstRow.add(fitnessButton);

        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        secondRow.add(spaButton);
        secondRow.add(contactUsButton);

        List<InlineKeyboardButton> thirdRow = new ArrayList<>();
        thirdRow.add(sberQrButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(Arrays.asList(firstRow, secondRow, thirdRow));

        inlineKeyboardMarkup.setKeyboard(rowList);

        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }

    @Autowired
    public void setSaleService(SaleService saleService) {
        this.saleService = saleService;
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
    public void setUserEntityService(UserEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }
}

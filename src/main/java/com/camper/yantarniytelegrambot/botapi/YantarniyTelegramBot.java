package com.camper.yantarniytelegrambot.botapi;

import com.camper.yantarniytelegrambot.entity.*;
import com.camper.yantarniytelegrambot.enums.ScheduleType;
import com.camper.yantarniytelegrambot.enums.UserRole;
import com.camper.yantarniytelegrambot.handlers.BotActionListener;
import com.camper.yantarniytelegrambot.services.*;
import com.camper.yantarniytelegrambot.utils.Utils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.groupadministration.SetChatPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Instant;
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
    private ScheduleService scheduleService;

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

    @SneakyThrows
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
                case "Главное меню":
                case "/start": {
                    if (userEntityService.findUserByChatId(chatId) == null) {
                        User user = update.getMessage().getFrom();
                        String firstName = user.getFirstName();
                        String lastName = user.getLastName();
                        String username = user.getUserName();
                        UserEntity newUser = new UserEntity(chatId, firstName, lastName, username, null, UserRole.USER,
                                new Timestamp(System.currentTimeMillis()),
                                new Timestamp(System.currentTimeMillis()));
                        userEntityService.putIfAbsent(newUser);
                        execute(sendReplyMarkup(chatId));
                    } else {
                        UserEntity user = userEntityService.findUserByChatId(chatId);
                        user.setLastEntry(new Timestamp(System.currentTimeMillis()));
                        userEntityService.saveUser(user);
                    }
                    return createMainMenuMessage(chatId, localeMessageSource.getMessage("mainMenu.menuLabel"));
                }
                case "/auto": {
                    /*Schedule schedule = scheduleService.findByType(ScheduleType.DEFAULT);
                    try {
                        schedule.setImage(Files.readAllBytes(Paths.get("C:\\Users\\sashc\\Desktop\\Телеграм бот\\Photos\\Schedules\\schedule2.jpeg")));
                        scheduleService.saveSchedule(schedule);
                        int x = 0;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
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
        InlineKeyboardButton scheduleButton = new InlineKeyboardButton("Расписание");
        InlineKeyboardButton spaButton = new InlineKeyboardButton("СПА");
        InlineKeyboardButton contactUsButton = new InlineKeyboardButton("Связаться с менеджером");
        InlineKeyboardButton coachesButton = new InlineKeyboardButton("Тренерский состав");
        InlineKeyboardButton sberQrButton = new InlineKeyboardButton("Плати QR от Сбера");

        clubCartsButton.setCallbackData("handleClubCardButton");
        scheduleButton.setCallbackData("handleSchedulesMenuButton");
        spaButton.setCallbackData("handleSpaButton");
        contactUsButton.setCallbackData("handleContactUsButton");
        coachesButton.setCallbackData("handleFitnessButton");
        sberQrButton.setCallbackData("handleQrSberButton");

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(clubCartsButton);
        firstRow.add(scheduleButton);

        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        secondRow.add(spaButton);
        secondRow.add(contactUsButton);

        List<InlineKeyboardButton> thirdRow = new ArrayList<>();
        thirdRow.add(coachesButton);

        List<InlineKeyboardButton> fourthRow = new ArrayList<>();
        fourthRow.add(sberQrButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(Arrays.asList(firstRow, secondRow, thirdRow, fourthRow));

        inlineKeyboardMarkup.setKeyboard(rowList);

        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }

    public static SendMessage sendReplyMarkup(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId,"При необходимости открыть главное меню воспользуйтесь " +
                "командой /start или соответствующей кнопкой в нижней части экрана");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow firstRow = new KeyboardRow();
        KeyboardButton button = new KeyboardButton("Главное меню");
        firstRow.add(button);

        keyboard.add(firstRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        return sendMessage;
    }

    @Autowired
    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
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

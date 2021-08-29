package com.camper.yantarniytelegrambot.botapi;

import com.camper.yantarniytelegrambot.entity.*;
import com.camper.yantarniytelegrambot.entity.Location;
import com.camper.yantarniytelegrambot.enums.ScheduleType;
import com.camper.yantarniytelegrambot.enums.UserRole;
import com.camper.yantarniytelegrambot.handlers.BotActionListener;
import com.camper.yantarniytelegrambot.services.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.*;

@Slf4j
@SuppressWarnings("unchecked")
public class YantarniyTelegramBot extends TelegramLongPollingBot {
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
    public void onUpdateReceived(Update update) {
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
                //e.printStackTrace();
                log.error("Callback query exception");
            }
        }

        try {
            //Commands and nonCommands messages handling
            if (update.getMessage() != null && update.getMessage().hasText()) {
                String text = update.getMessage().getText();
                String chatId = update.getMessage().getChatId().toString();
                switch (text) {
                    case "Главное меню":
                    case "/start": {
                        UserEntity userEntity = userEntityService.findUserByChatId(chatId);
                        if (userEntity == null) {
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
                            userEntity.setLastEntry(new Timestamp(System.currentTimeMillis()));
                            userEntityService.saveUser(userEntity);
                        }
                        execute(createMainMenuMessage(chatId, localeMessageSource.getMessage("mainMenu.menuLabel")));
                    }
                    case "/auto": {
                    /*try {
                        Schedule schedule = new Schedule(Files.readAllBytes(Paths.get("C:\\Users\\sashc\\Desktop\\Телеграм бот\\Photos\\Schedules\\schedule2.jpeg")), null, ScheduleType.DEFAULT);
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
                        execute(createMainMenuMessage(chatId, localeMessageSource.getMessage("other.unknownNonCommandMessage")));
                    }
                }
            }
        } catch (TelegramApiException e) {
            //e.printStackTrace();
            log.error("Commands and none commands handling exception");
        }
    }

//    @Override
//    public String getBotPath() {
//        return WEB_HOOK_PATH;
//    }
//
//    @Override
//    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
//
//        //CallbackQuery handling
//        if (update.hasCallbackQuery() && !update.getCallbackQuery().getData().equals("null")) {
//            CallbackQuery query = update.getCallbackQuery();
//            String chatId = query.getMessage().getChatId().toString();
//            String data = update.getCallbackQuery().getData();
//            Method handler = handlers.get(data);
//            try {
//                if (handler != null) {
//                    List<PartialBotApiMethod<?>> answers = (List<PartialBotApiMethod<?>>) handler.invoke(botActionListener, chatId, query);
//                    if (answers != null) {
//                        for (PartialBotApiMethod<?> answer : answers) {
//                            if (answer instanceof BotApiMethod<?>) {
//                                execute((BotApiMethod<? extends Serializable>) answer);
//                            } else if (answer instanceof EditMessageMedia) {
//                                execute((EditMessageMedia) answer);
//                            } else if (answer instanceof SendPhoto) {
//                                execute((SendPhoto) answer);
//                            }
//                        }
//                    }
//                } else {
//                    log.warn("Not found handler for selected button: \"" + query.getMessage().getText() + "\", and callback query value = " + query.getData());
//                }
//            } catch (IllegalAccessException | InvocationTargetException | TelegramApiException e) {
//                //e.printStackTrace();
//                log.error("Callback query exception");
//            }
//        }
//
//        try {
//            //Commands and nonCommands messages handling
//            if (update.getMessage() != null && update.getMessage().hasText()) {
//                String text = update.getMessage().getText();
//                String chatId = update.getMessage().getChatId().toString();
//                switch (text) {
//                    case "Главное меню":
//                    case "/start": {
//                        UserEntity userEntity = userEntityService.findUserByChatId(chatId);
//                        if (userEntity == null) {
//                            User user = update.getMessage().getFrom();
//                            String firstName = user.getFirstName();
//                            String lastName = user.getLastName();
//                            String username = user.getUserName();
//                            UserEntity newUser = new UserEntity(chatId, firstName, lastName, username, null, UserRole.USER,
//                                    new Timestamp(System.currentTimeMillis()),
//                                    new Timestamp(System.currentTimeMillis()));
//                            userEntityService.putIfAbsent(newUser);
//                            execute(sendReplyMarkup(chatId));
//                        } else {
//                            userEntity.setLastEntry(new Timestamp(System.currentTimeMillis()));
//                            userEntityService.saveUser(userEntity);
//                        }
//                        execute(createMainMenuMessage(chatId, localeMessageSource.getMessage("mainMenu.menuLabel")));
//                    }
//                    case "/auto": {
//                    /*try {
//                        Schedule schedule = new Schedule(Files.readAllBytes(Paths.get("C:\\Users\\sashc\\Desktop\\Телеграм бот\\Photos\\Schedules\\schedule2.jpeg")), null, ScheduleType.DEFAULT);
//                        scheduleService.saveSchedule(schedule);
//                        int x = 0;
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }*/
//                    /*List<Sale> sales = saleService.findAll();
//                    List<UserEntity> users = userEntityService.findAll();
//                    for (UserEntity user : users) {
//                        if (user.getRole().equals(UserRole.ADMIN)) {
//                            continue;
//                        }
//                        for (Sale sale : sales) {
//                            if (sale.getImage() != null) {
//                                SendPhoto.SendPhotoBuilder builder = SendPhoto.builder();
//                                builder.chatId(user.getChatId());
//                                builder.photo(new InputFile(new ByteArrayInputStream(sale.getImage()), "filename"));
//                                try {
//                                    execute(builder.build());
//                                } catch (TelegramApiException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                    }*/
//                        break;
//                    }
//                    default: {
//                       execute(createMainMenuMessage(chatId, localeMessageSource.getMessage("other.unknownNonCommandMessage")));
//                    }
//                }
//            }
//        } catch (TelegramApiException e) {
//            //e.printStackTrace();
//            log.error("Commands and none commands handling exception");
//        }
//
//        return null;
//    }

    public static SendMessage createMainMenuMessage(String chatId, String text) {
        SendMessage sendMessage = new SendMessage(chatId, text);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton salesButton = new InlineKeyboardButton("Акции");
        InlineKeyboardButton scheduleButton = new InlineKeyboardButton("Расписание");
        InlineKeyboardButton clubCartsButton = new InlineKeyboardButton("Клубные карты");
        InlineKeyboardButton spaServicesButton = new InlineKeyboardButton("Прайс-СПА");
        InlineKeyboardButton employeesButton = new InlineKeyboardButton("Сотрудники");
        InlineKeyboardButton sberQrButton = new InlineKeyboardButton("Плати QR от Сбера");
        InlineKeyboardButton contactAdminButton = new InlineKeyboardButton("Запись в СПА");
        InlineKeyboardButton contactManagerButton = new InlineKeyboardButton("Связь с менеджером");



        salesButton.setCallbackData("handleSalesButton");
        scheduleButton.setCallbackData("handleSchedulesMenuButton");
        clubCartsButton.setCallbackData("handleClubCardButton");
        spaServicesButton.setCallbackData("handleSpaServiceMenuButton");
        employeesButton.setCallbackData("handleEmployeeMenuButton");
        sberQrButton.setCallbackData("handleQrSberButton");
        contactAdminButton.setCallbackData("handleContactAdminButton");
        contactManagerButton.setCallbackData("handleContactManagerButton");

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(employeesButton);
        firstRow.add(sberQrButton);

        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        secondRow.add(clubCartsButton);
        secondRow.add(scheduleButton);

        List<InlineKeyboardButton> thirdRow = new ArrayList<>();
        thirdRow.add(salesButton);
        thirdRow.add(spaServicesButton);

        List<InlineKeyboardButton> fourthRow = new ArrayList<>();
        fourthRow.add(contactManagerButton);
        fourthRow.add(contactAdminButton);

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

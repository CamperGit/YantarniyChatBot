package com.camper.yantarniytelegrambot.botapi;

import com.camper.yantarniytelegrambot.entity.Employee;
import com.camper.yantarniytelegrambot.entity.EmployeeType;
import com.camper.yantarniytelegrambot.entity.Location;
import com.camper.yantarniytelegrambot.handlers.BotActionListener;
import com.camper.yantarniytelegrambot.services.EmployeeService;
import com.camper.yantarniytelegrambot.services.EmployeeTypeService;
import com.camper.yantarniytelegrambot.services.LocaleMessageSource;
import com.camper.yantarniytelegrambot.services.LocationService;
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
    private LocationService locationService;
    private EmployeeService employeeService;
    private EmployeeTypeService employeeTypeService;

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
                    /*List<Employee> employees = employeeService.findAll();
                    for (Employee employee : employees) {
                        SendPhoto.SendPhotoBuilder builder = SendPhoto.builder();
                        builder.chatId(chatId);
                        builder.photo(new InputFile(new ByteArrayInputStream(employee.getImage()),"filename"));
                        try {
                            execute(builder.build());
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }*/
                    break;
                }
                default : {
                    /*try {
                        Location tz = locationService.findLocationByTitle("GYM");
                        Location zpp = locationService.findLocationByTitle("GROUP_ACTIVITY");
                        Location pool = locationService.findLocationByTitle("POOL");
                        EmployeeType coach = employeeTypeService.findEmployeeTypeByType("COACH");
                        EmployeeType master = employeeTypeService.findEmployeeTypeByType("MASTER_COACH");
                        EmployeeType masterPl = employeeTypeService.findEmployeeTypeByType("MASTER_COACH_PLUS");
                        Employee tz1 = new Employee(Files.readAllBytes(Paths.get("C:\\Users\\sashc\\Desktop\\Телеграм бот\\Photos\\Coaches\\tz\\gendlin.png")),
                                null,"Евгений","Гендлин",tz,masterPl);
                        Employee tz2 = new Employee(Files.readAllBytes(Paths.get("C:\\Users\\sashc\\Desktop\\Телеграм бот\\Photos\\Coaches\\tz\\kuszn.png")),
                                null,"Роман","Кузнецов",tz,master);
                        Employee zpp1 = new Employee(Files.readAllBytes(Paths.get("C:\\Users\\sashc\\Desktop\\Телеграм бот\\Photos\\Coaches\\zpp\\cheprasova.png")),
                                null,"Роман","Кузнецов",zpp,coach);
                        Employee zpp2 = new Employee(Files.readAllBytes(Paths.get("C:\\Users\\sashc\\Desktop\\Телеграм бот\\Photos\\Coaches\\zpp\\kamish.png")),
                                null,"Роман","Кузнецов",zpp,master);
                        Employee pool1 = new Employee(Files.readAllBytes(Paths.get("C:\\Users\\sashc\\Desktop\\Телеграм бот\\Photos\\Coaches\\pool\\avdeev.png")),
                                null,"Александр","Авдеев",pool,coach);
                        Employee pool2 = new Employee(Files.readAllBytes(Paths.get("C:\\Users\\sashc\\Desktop\\Телеграм бот\\Photos\\Coaches\\pool\\gusev.png")),
                                null,"Андрей","Гусев",pool,master);

                        employeeService.putIfAbsent(tz1);
                        employeeService.putIfAbsent(tz2);
                        employeeService.putIfAbsent(zpp1);
                        employeeService.putIfAbsent(zpp2);
                        employeeService.putIfAbsent(pool1);
                        employeeService.putIfAbsent(pool2);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
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

    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Autowired
    public void setEmployeeTypeService(EmployeeTypeService employeeTypeService) {
        this.employeeTypeService = employeeTypeService;
    }
}

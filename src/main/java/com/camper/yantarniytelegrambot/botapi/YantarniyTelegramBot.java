package com.camper.yantarniytelegrambot.botapi;

import com.camper.yantarniytelegrambot.handlers.BotActionListener;
import com.camper.yantarniytelegrambot.services.LocaleMessageSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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
        if (update.hasCallbackQuery()) {
            CallbackQuery query = update.getCallbackQuery();
            String chatId = query.getMessage().getChatId().toString();
            if (update.hasCallbackQuery()) {
                Method handler = handlers.get(update.getCallbackQuery().getData());
                try {
                    if (handler != null) {
                        List<BotApiMethod<?>> answers = (List<BotApiMethod<?>>) handler.invoke(botActionListener,chatId);

                        if (answers != null) {
                            for (BotApiMethod<?> answer : answers) {
                                execute(answer);
                            }
                        }
                        /*if (answer instanceof SendMessage) {
                            execute((SendMessage)answer);
                        } else if (answer instanceof SendPhoto) {

                        }*/
                    } else {
                        log.warn("Not found handler for selected button: \"" + query.getMessage().getText() + "\", and callback query value = " + query.getData());
                    }
                } catch (IllegalAccessException | InvocationTargetException | TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }

        //Commands and nonCommands messages handling
        if (update.getMessage() != null && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();
            switch (text) {
                case "/start": {
                    SendMessage sendMessage = new SendMessage(chatId, localeMessageSource.getMessage("mainMenu.menuLabel"));
                    sendMessage.setReplyMarkup(getMainMenuButtons());
                    return sendMessage;
                }
                default : {
                    SendMessage sendMessage = new SendMessage(chatId, localeMessageSource.getMessage("other.unknownNonCommandMessage"));
                    sendMessage.setReplyMarkup(getMainMenuButtons());
                    return sendMessage;
                }
            }

            /*final List<PhotoSize> photos = update.getMessage().getPhoto();
            if (photos != null) {

                byte[] image = null;
                String locationString = null;
                String descriptionString = null;

                Optional<PhotoSize> photoSize = photos.stream().max(Comparator.comparing(PhotoSize::getFileSize));
                String photoId = photoSize.orElseThrow(IllegalStateException::new).getFileId();

                URL url;
                try {
                    url = new URL("https://api.telegram.org/bot"+TOKEN+"/getFile?file_id="+photoId);
                    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

                    JSONObject jResult = new JSONObject(in.readLine());
                    JSONObject path = jResult.getJSONObject("result");
                    String filePath = path.getString("file_path");

                    File localFile = new File("uploadedFiles" );
                    InputStream is = new URL("https://api.telegram.org/file/bot" + TOKEN + "/" + filePath).openStream();

                    FileUtils.copyInputStreamToFile(is,localFile);
                    image = Files.readAllBytes(Paths.get(localFile.getPath()));

                    Files.delete(Paths.get(localFile.getPath()));

                    is.close();
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (update.getMessage().hasText()) {
                    String[] strings = update.getMessage().getText().split(";");
                    if (strings.length != 0) {
                        locationString = strings[0];
                        descriptionString = strings[1];
                    }
                }

                Location location = locationService.putIfAbsent(new Location("Клубная карта", new ArrayList<>(), new ArrayList<>()));
                Sale sale = new Sale(image,descriptionString,location);
                saleService.putIfAbsent(sale);
            }*/

            /*if (update.getMessage().hasText()) {
                String chatId = update.getMessage().getChatId().toString();
                try {
                    execute(new SendMessage(chatId,"Hi " + update.getMessage().getText()));
                    List<CardType> cards = cardTypeService.findAll();
                    for (CardType card : cards) {
                        execute(new SendMessage(chatId,card.getTitle()));
                    }

                    List<Sale> sales = saleService.findAll();
                    for (Sale sale : sales) {
                        if (sale.getImage() != null) {
                            SendPhoto.SendPhotoBuilder builder = SendPhoto.builder();
                            builder.chatId(chatId);
                            builder.photo(new InputFile(new ByteArrayInputStream(sale.getImage()),"filename"));
                            execute(builder.build());
                        } else if (sale.getDescription() != null) {

                        }
                    }
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }*/
        }
        return null;
    }


    private InlineKeyboardMarkup getMainMenuButtons() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton clubCartsButton = new InlineKeyboardButton(localeMessageSource.getMessage("mainMenu.clubCartsButton"));
        InlineKeyboardButton fitnessButton = new InlineKeyboardButton(localeMessageSource.getMessage("mainMenu.fitnessButton"));
        InlineKeyboardButton spaButton = new InlineKeyboardButton(localeMessageSource.getMessage("mainMenu.spaButton"));
        InlineKeyboardButton contactUsButton = new InlineKeyboardButton(localeMessageSource.getMessage("mainMenu.contactUsButton"));

        clubCartsButton.setCallbackData("handleClubCartButton");
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

    @Autowired
    public void setLocaleMessageSource(LocaleMessageSource localeMessageSource) {
        this.localeMessageSource = localeMessageSource;
    }



    @Autowired
    public void setBotActionListener(BotActionListener botActionListener) {
        this.botActionListener = botActionListener;
    }
}

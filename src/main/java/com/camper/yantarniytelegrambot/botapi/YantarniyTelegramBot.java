package com.camper.yantarniytelegrambot.botapi;

import com.camper.yantarniytelegrambot.entity.CardType;
import com.camper.yantarniytelegrambot.entity.Location;
import com.camper.yantarniytelegrambot.entity.Sale;
import com.camper.yantarniytelegrambot.services.CardTypeService;
import com.camper.yantarniytelegrambot.services.LocationService;
import com.camper.yantarniytelegrambot.services.SaleService;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class YantarniyTelegramBot extends TelegramWebhookBot {
    private final String WEB_HOOK_PATH;
    private final String USERNAME;
    private final String TOKEN;
    private CardTypeService cardTypeService;
    private LocationService locationService;
    private SaleService saleService;

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
        if (update.getMessage() != null) {
            final List<PhotoSize> photos = update.getMessage().getPhoto();
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
            }

            if (update.getMessage().hasText()) {
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
            }
        }


        return null;
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
    public void setCardTypeService(CardTypeService cardTypeService) {
        this.cardTypeService = cardTypeService;
    }
}

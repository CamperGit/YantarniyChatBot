package com.camper.yantarniytelegrambot.handlers;

import com.camper.yantarniytelegrambot.services.LocaleMessageSource;
import com.camper.yantarniytelegrambot.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class SberQRButtonHandler implements BotButtonHandler{
    private LocaleMessageSource localeMessageSource;

    @Override
    public List<PartialBotApiMethod<?>> handle(String chatId, CallbackQuery query) {
        List<PartialBotApiMethod<?>> answers = new ArrayList<>();
        answers.add(Utils.deleteMessage(chatId,query.getMessage().getMessageId()));

        SendPhoto.SendPhotoBuilder builder = SendPhoto.builder();
        builder.chatId(chatId);
        builder.photo(new InputFile(Thread.currentThread().getContextClassLoader().getResourceAsStream("other/sberqr.png"), "sberqr.png"));
        String caption = "Инструкция для «Плати QR» от Сбера:\n " +
                "1. Сохранить QR код в галерею или сделать скриншот\n " +
        "2. Открыть мобильное приложение Сбербанк Онлайн, выбрать «Платежи» и далее — «Оплата по QR или штрих коду»\n" +
        "3. Нажать загрузить изображение и выбрать в галерее QR-код\n" +
        "4. Ввести сумму покупки и подтвердить оплату\n" +
        "5. По номеру телефона +74732020302 сообщить ФИО покупателя, код авторизации и название оплаченной услуги\n" +
        "6. Покупка оформлена\n";
        builder.caption(caption);
        builder.replyMarkup(BotButtonHandler.getReturnMarkup("handleReturnMainMenuButton", false));
        answers.add(builder.build());
        return answers;
    }

    @Autowired
    public void setLocaleMessageSource(LocaleMessageSource localeMessageSource) {
        this.localeMessageSource = localeMessageSource;
    }
}

package com.camper.yantarniytelegrambot.utils;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static EditMessageText changeMessage(String text, String chatId, Integer messageId, InlineKeyboardMarkup markup) {
        EditMessageText newText = new EditMessageText();
        newText.setText(text);
        newText.setChatId(chatId);
        newText.setMessageId(messageId);
        newText.setReplyMarkup(markup);

        return newText;
    }

    public static DeleteMessage deleteMessage(String chatId, Integer messageId) {
        return new DeleteMessage(chatId,messageId);
    }
}

package com.camper.yantarniytelegrambot.utils;

import com.camper.yantarniytelegrambot.botapi.YantarniyTelegramBot;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {
    /**
     * Change message text
     *
     * @param text      - new text
     * @param chatId    - chat id
     * @param messageId - message id
     * @param markup    - menu markup
     * @return object to change text of message after executing
     */
    public static EditMessageText changeMessage(String text, String chatId, Integer messageId, InlineKeyboardMarkup markup) {
        EditMessageText newText = new EditMessageText();
        newText.setText(text);
        newText.setChatId(chatId);
        newText.setMessageId(messageId);
        newText.setReplyMarkup(markup);
        return newText;
    }

    /**
     * Delete selected message
     *
     * @param chatId    - chat id
     * @param messageId - message id
     * @return object to delete the message after executing
     */
    public static DeleteMessage deleteMessage(String chatId, Integer messageId) {
        return new DeleteMessage(chatId, messageId);
    }

    /**
     * @param chatId      - chat id of message
     * @param messageId   - id of message
     * @param markup      - menu markup
     * @param image       - image to change
     * @param description - optional, added text in photo message
     * @return object to change the photo after executing
     */
    public static EditMessageMedia changeMessagePhoto(String chatId, Integer messageId, InlineKeyboardMarkup markup,
                                                      byte[] image, String description) {
        EditMessageMedia editMessageMedia = new EditMessageMedia();
        editMessageMedia.setChatId(chatId);
        editMessageMedia.setMessageId(messageId);
        editMessageMedia.setReplyMarkup(markup);

        InputMedia in = new InputMediaPhoto();

        if (description != null) {
            in.setCaption(description);
        }

        in.setMedia(new ByteArrayInputStream(image), "filename");
        editMessageMedia.setMedia(in);

        return editMessageMedia;
    }

    /**
     * Change previous image/message description on new image/description
     *
     * @param chatId      - chat id
     * @param messageId   - message id
     * @param query       - callback query
     * @param markup      - markup
     * @param image       - new image
     * @param description - new message description
     * @return list with commands to execute
     */
    public static List<PartialBotApiMethod<?>> scrollMenuItem(String chatId, Integer messageId, CallbackQuery query
            , InlineKeyboardMarkup markup, byte[] image, String description) {
        List<PartialBotApiMethod<?>> answers = new ArrayList<>();

        if (image != null) {
            if (query.getMessage().hasPhoto()) {
                answers.add(Utils.changeMessagePhoto(chatId, messageId, markup, image, description));
            } else {
                answers.add(Utils.deleteMessage(chatId, messageId));
                SendPhoto.SendPhotoBuilder builder = SendPhoto.builder();
                builder.chatId(chatId);
                builder.photo(new InputFile(new ByteArrayInputStream(image), "filename"));
                builder.replyMarkup(markup);

                if (description != null) {
                    builder.caption(description);
                }
                answers.add(builder.build());
            }

        } else {
            if (query.getMessage().hasPhoto()) {
                answers.add(Utils.deleteMessage(chatId, messageId));
                SendMessage sendMessage = new SendMessage(chatId, description);
                sendMessage.setReplyMarkup(markup);
                answers.add(sendMessage);
            } else {
                answers.add(Utils.changeMessage(description
                        , chatId
                        , messageId
                        , markup));
            }
        }
        return answers;
    }

    /**
     * delete last message and create main menu message
     *
     * @param chatId    - chat id
     * @param messageId - id of last message
     * @return commands to delete last message and create main menu
     */
    public static List<PartialBotApiMethod<?>> moveToMainMenu(String chatId, Integer messageId) {
        return new ArrayList<>(Arrays.asList(Utils.deleteMessage(chatId, messageId),
                YantarniyTelegramBot.createMainMenuMessage(chatId, "Главное меню")));
    }
}

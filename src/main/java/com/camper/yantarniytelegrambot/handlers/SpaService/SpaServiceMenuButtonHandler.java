package com.camper.yantarniytelegrambot.handlers.SpaService;

import com.camper.yantarniytelegrambot.handlers.BotButtonHandler;
import com.camper.yantarniytelegrambot.services.LocaleMessageSource;
import com.camper.yantarniytelegrambot.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SpaServiceMenuButtonHandler implements BotButtonHandler {
    private LocaleMessageSource localeMessageSource;

    @Override
    public List<PartialBotApiMethod<?>> handle(String chatId, CallbackQuery query) {
        return new ArrayList<>(Utils.changeMessage(localeMessageSource.getMessage("onAction.spaService"),
                chatId,
                query.getMessage(),
                getSpaServicesMenuMarkup()));
    }

    public List<PartialBotApiMethod<?>> openNailsMenu(String chatId, CallbackQuery query) {
        return new ArrayList<>(Arrays.asList(Utils.deleteMessage(chatId, query.getMessage().getMessageId()),
                SendMessage.builder()
                        .chatId(chatId)
                        .text(localeMessageSource.getMessage("onAction.spaService.nails"))
                        .replyMarkup(getSCNailsMenuMarkup())
                        .build()));
    }

    public List<PartialBotApiMethod<?>> openFaceMenu(String chatId, CallbackQuery query) {
        return new ArrayList<>(Arrays.asList(Utils.deleteMessage(chatId, query.getMessage().getMessageId()),
                SendMessage.builder()
                        .chatId(chatId)
                        .text(localeMessageSource.getMessage("onAction.spaService.face"))
                        .replyMarkup(getSCFaceMenuMarkup())
                        .build()));
    }

    public List<PartialBotApiMethod<?>> openBodyMenu(String chatId, CallbackQuery query) {
        return new ArrayList<>(Arrays.asList(Utils.deleteMessage(chatId, query.getMessage().getMessageId()),
                SendMessage.builder()
                        .chatId(chatId)
                        .text(localeMessageSource.getMessage("onAction.spaService.body"))
                        .replyMarkup(getSCBodyMenuMarkup())
                        .build()));
    }

    private InlineKeyboardMarkup getSpaServicesMenuMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton nailsButton = new InlineKeyboardButton(localeMessageSource.getMessage("spaServices.nails"));
        InlineKeyboardButton faceButton = new InlineKeyboardButton(localeMessageSource.getMessage("spaServices.face"));
        InlineKeyboardButton hairButton = new InlineKeyboardButton(localeMessageSource.getMessage("spaServices.hair"));
        InlineKeyboardButton bodyButton = new InlineKeyboardButton(localeMessageSource.getMessage("spaServices.body"));
        InlineKeyboardButton bathhouseButton = new InlineKeyboardButton(localeMessageSource.getMessage("spaServices.bathhouse"));
        InlineKeyboardButton returnButton = new InlineKeyboardButton(localeMessageSource.getMessage("other.moveBack"));

        nailsButton.setCallbackData("handleSCNailsButton");
        faceButton.setCallbackData("handleSCFaceButton");
        hairButton.setCallbackData("handleSCHairButton");
        bodyButton.setCallbackData("handleSCBodyButton");
        bathhouseButton.setCallbackData("handleSCBathhouseButton");
        returnButton.setCallbackData("handleReturnMainMenuButton");

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(nailsButton);
        firstRow.add(faceButton);
        firstRow.add(hairButton);

        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        secondRow.add(bodyButton);
        secondRow.add(bathhouseButton);

        List<InlineKeyboardButton> thirdRow = new ArrayList<>();
        thirdRow.add(returnButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(Arrays.asList(firstRow, secondRow, thirdRow));

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    private InlineKeyboardMarkup getSCNailsMenuMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton manicureButton = new InlineKeyboardButton(localeMessageSource.getMessage("spaServices.nails.manicure"));
        InlineKeyboardButton nailExtensionButton = new InlineKeyboardButton(localeMessageSource.getMessage("spaServices.nails.nailsExtension"));
        InlineKeyboardButton pedicureButton = new InlineKeyboardButton(localeMessageSource.getMessage("spaServices.nails.pedicure"));
        InlineKeyboardButton topMasterButton = new InlineKeyboardButton(localeMessageSource.getMessage("spaServices.nails.topMaster"));
        InlineKeyboardButton returnButton = new InlineKeyboardButton(localeMessageSource.getMessage("other.moveBack"));
        InlineKeyboardButton mainMenuButton = new InlineKeyboardButton(localeMessageSource.getMessage("other.moveMainMenu"));

        manicureButton.setCallbackData("handleSCManicureButton");
        nailExtensionButton.setCallbackData("handleSCNailExtensionButton");
        pedicureButton.setCallbackData("handleSCPedicureButton");
        topMasterButton.setCallbackData("handleSCTopMasterButton");
        returnButton.setCallbackData("handleSpaServiceMenuButton");
        mainMenuButton.setCallbackData("handleReturnMainMenuButton");

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(manicureButton);
        firstRow.add(pedicureButton);
        firstRow.add(topMasterButton);

        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        secondRow.add(nailExtensionButton);

        List<InlineKeyboardButton> thirdRow = new ArrayList<>();
        thirdRow.add(returnButton);
        thirdRow.add(mainMenuButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(Arrays.asList(firstRow, secondRow, thirdRow));

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    private InlineKeyboardMarkup getSCFaceMenuMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton rfLiftingButton = new InlineKeyboardButton(localeMessageSource.getMessage("spaServices.face.rfLifting"));
        InlineKeyboardButton lpgButton = new InlineKeyboardButton(localeMessageSource.getMessage("spaServices.face.lpg"));
        InlineKeyboardButton jetPeelButton = new InlineKeyboardButton(localeMessageSource.getMessage("spaServices.face.jetPeel"));
        InlineKeyboardButton chemPeelButton = new InlineKeyboardButton(localeMessageSource.getMessage("spaServices.face.chemPeel"));
        InlineKeyboardButton declareButton = new InlineKeyboardButton(localeMessageSource.getMessage("spaServices.face.declare"));
        InlineKeyboardButton returnButton = new InlineKeyboardButton(localeMessageSource.getMessage("other.moveBack"));
        InlineKeyboardButton mainMenuButton = new InlineKeyboardButton(localeMessageSource.getMessage("other.moveMainMenu"));

        rfLiftingButton.setCallbackData("handleSCRfLiftingButton");
        lpgButton.setCallbackData("handleSCLpgButton");
        jetPeelButton.setCallbackData("handleSCJetPeelButton");
        chemPeelButton.setCallbackData("handleSCChemPeelButton");
        declareButton.setCallbackData("handleSCDeclareButton");
        returnButton.setCallbackData("handleSpaServiceMenuButton");
        mainMenuButton.setCallbackData("handleReturnMainMenuButton");

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(rfLiftingButton);
        firstRow.add(declareButton);

        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        secondRow.add(jetPeelButton);
        secondRow.add(chemPeelButton);

        List<InlineKeyboardButton> thirdRow = new ArrayList<>();
        thirdRow.add(lpgButton);

        List<InlineKeyboardButton> fourthRow = new ArrayList<>();
        fourthRow.add(returnButton);
        fourthRow.add(mainMenuButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(Arrays.asList(firstRow, secondRow, thirdRow, fourthRow));

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    private InlineKeyboardMarkup getSCBodyMenuMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton lpgCelluM6Button = new InlineKeyboardButton(localeMessageSource.getMessage("spaServices.body.lpgCelluM6"));
        InlineKeyboardButton massageButton = new InlineKeyboardButton(localeMessageSource.getMessage("spaServices.body.massage"));
        InlineKeyboardButton bodyTreatmentsButton = new InlineKeyboardButton(localeMessageSource.getMessage("spaServices.body.bodyTreatments"));
        InlineKeyboardButton epilationButton = new InlineKeyboardButton(localeMessageSource.getMessage("spaServices.body.epilation"));
        InlineKeyboardButton shugaringButton = new InlineKeyboardButton(localeMessageSource.getMessage("spaServices.body.shugaring"));
        InlineKeyboardButton solariumButton = new InlineKeyboardButton(localeMessageSource.getMessage("spaServices.body.solarium"));
        InlineKeyboardButton returnButton = new InlineKeyboardButton(localeMessageSource.getMessage("other.moveBack"));
        InlineKeyboardButton mainMenuButton = new InlineKeyboardButton(localeMessageSource.getMessage("other.moveMainMenu"));

        lpgCelluM6Button.setCallbackData("handleSCLpgCelluM6Button");
        massageButton.setCallbackData("handleSCMassageButton");
        bodyTreatmentsButton.setCallbackData("handleSCBodyTreatmentsButton");
        epilationButton.setCallbackData("handleSCEpilationButton");
        shugaringButton.setCallbackData("handleSCSchugaringButton");
        solariumButton.setCallbackData("handleSCSolariumButton");
        returnButton.setCallbackData("handleSpaServiceMenuButton");
        mainMenuButton.setCallbackData("handleReturnMainMenuButton");

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(massageButton);
        firstRow.add(bodyTreatmentsButton);
        firstRow.add(solariumButton);

        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        secondRow.add(lpgCelluM6Button);

        List<InlineKeyboardButton> thirdRow = new ArrayList<>();
        thirdRow.add(epilationButton);
        thirdRow.add(shugaringButton);

        List<InlineKeyboardButton> fourthRow = new ArrayList<>();
        fourthRow.add(returnButton);
        fourthRow.add(mainMenuButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(Arrays.asList(firstRow, secondRow, thirdRow, fourthRow));

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    @Autowired
    public void setLocaleMessageSource(LocaleMessageSource localeMessageSource) {
        this.localeMessageSource = localeMessageSource;
    }
}

package com.camper.yantarniytelegrambot.handlers;


import com.camper.yantarniytelegrambot.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;

@Service
public class BotActionListener {
    private HandlersFacade handlersFacade;

    public List<PartialBotApiMethod<?>> handleClubCardButton(String chatId, CallbackQuery query) {
        return handlersFacade.getClubCardButtonHandler().handle(chatId,query);
    }

    public List<PartialBotApiMethod<?>> handleClubCardsTypesButton(String chatId,CallbackQuery query) {
        return handlersFacade.getClubCardTypeButtonHandler().handle(chatId, query);
    }

    //Sales menu start
    public List<PartialBotApiMethod<?>> handleClubCardsSalesButton(String chatId,CallbackQuery query) {
        return handlersFacade.getClubCardSalesButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleClubCardsSalesPrevButton(String chatId,CallbackQuery query) {
        return handlersFacade.getClubCardSalesButtonHandler().previousSale(chatId,query);
    }

    public List<PartialBotApiMethod<?>> handleClubCardsSalesNextButton(String chatId,CallbackQuery query) {
        return handlersFacade.getClubCardSalesButtonHandler().nextSale(chatId,query);
    }

    public List<PartialBotApiMethod<?>> handleClubCardsSalesReturnButton(String chatId,CallbackQuery query) {
        return handlersFacade.getClubCardSalesButtonHandler().returnToMainMenu(chatId, query);
    }
    //Sales menu end

    //Fitness menu start

    public List<PartialBotApiMethod<?>> handleFitnessButton(String chatId, CallbackQuery query) {
        return handlersFacade.getFitnessButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleFitnessCoachesButton(String chatId, CallbackQuery query) {
        return handlersFacade.getFitnessButtonHandler().openCoachesMenu(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleFitnessSchedulesButton(String chatId, CallbackQuery query) {
        return handlersFacade.getFitnessButtonHandler().openSchedulesMenu(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleFitnessGymButton(String chatId, CallbackQuery query) {
        return handlersFacade.getFitnessButtonHandler().getFitnessGymButtonHandler().handle(chatId, query);
    }

    //GA Start
    public List<PartialBotApiMethod<?>> handleFitnessGroupsButton(String chatId, CallbackQuery query) {
        return handlersFacade.getFitnessButtonHandler().getFitnessGroupActivityButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleFitnessGAPrevButton(String chatId, CallbackQuery query) {
        return handlersFacade.getFitnessButtonHandler().getFitnessGroupActivityButtonHandler().previousCoach(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleFitnessGANextButton(String chatId, CallbackQuery query) {
        return handlersFacade.getFitnessButtonHandler().getFitnessGroupActivityButtonHandler().nextCoach(chatId, query);
    }
    //GA End


    public List<PartialBotApiMethod<?>> handleFitnessPoolButton(String chatId, CallbackQuery query) {
        return handlersFacade.getFitnessButtonHandler().getFitnessPoolButtonHandler().handle(chatId, query);
    }

    //Fitness menu end

    public List<PartialBotApiMethod<?>> handleReturnMainMenuButton(String chatId,CallbackQuery query) {
        return Utils.moveToMainMenu(chatId,query.getMessage().getMessageId());
    }

    @Autowired
    public void setHandlersFacade(HandlersFacade handlersFacade) {
        this.handlersFacade = handlersFacade;
    }
}

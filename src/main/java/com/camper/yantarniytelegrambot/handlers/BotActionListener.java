package com.camper.yantarniytelegrambot.handlers;


import com.camper.yantarniytelegrambot.enums.ScrollState;
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
        return handlersFacade.getClubCardButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleClubCardsTypesButton(String chatId, CallbackQuery query) {
        return handlersFacade.getClubCardTypeButtonHandler().handle(chatId, query);
    }

    //Sales menu start
    public List<PartialBotApiMethod<?>> handleClubCardsSalesButton(String chatId, CallbackQuery query) {
        return handlersFacade.getClubCardSalesButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleClubCardsSalesPrevButton(String chatId, CallbackQuery query) {
        return handlersFacade.getClubCardSalesButtonHandler().previousSale(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleClubCardsSalesNextButton(String chatId, CallbackQuery query) {
        return handlersFacade.getClubCardSalesButtonHandler().nextSale(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleClubCardsSalesReturnButton(String chatId, CallbackQuery query) {
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

    //GA Start
    public List<PartialBotApiMethod<?>> handleFitnessGroupsButton(String chatId, CallbackQuery query) {
        return handlersFacade.getFitnessButtonHandler().getFitnessGroupActivityButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleFitnessGAPrevButton(String chatId, CallbackQuery query) {
        return handlersFacade.getFitnessButtonHandler().getFitnessGroupActivityButtonHandler().scrollItem(chatId, query, ScrollState.PREVIOUS);
    }

    public List<PartialBotApiMethod<?>> handleFitnessGANextButton(String chatId, CallbackQuery query) {
        return handlersFacade.getFitnessButtonHandler().getFitnessGroupActivityButtonHandler().scrollItem(chatId, query, ScrollState.NEXT);
    }
    //GA End


    //GYM Start
    public List<PartialBotApiMethod<?>> handleFitnessGymButton(String chatId, CallbackQuery query) {
        return handlersFacade.getFitnessButtonHandler().getFitnessGymButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleFitnessGymPrevButton(String chatId, CallbackQuery query) {
        return handlersFacade.getFitnessButtonHandler().getFitnessGymButtonHandler().scrollItem(chatId, query, ScrollState.PREVIOUS);
    }

    public List<PartialBotApiMethod<?>> handleFitnessGymNextButton(String chatId, CallbackQuery query) {
        return handlersFacade.getFitnessButtonHandler().getFitnessGymButtonHandler().scrollItem(chatId, query, ScrollState.NEXT);
    }
    //GYM End


    //POOL Start
    public List<PartialBotApiMethod<?>> handleFitnessPoolButton(String chatId, CallbackQuery query) {
        return handlersFacade.getFitnessButtonHandler().getFitnessPoolButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleFitnessPoolPrevButton(String chatId, CallbackQuery query) {
        return handlersFacade.getFitnessButtonHandler().getFitnessPoolButtonHandler().scrollItem(chatId, query, ScrollState.PREVIOUS);
    }

    public List<PartialBotApiMethod<?>> handleFitnessPoolNextButton(String chatId, CallbackQuery query) {
        return handlersFacade.getFitnessButtonHandler().getFitnessPoolButtonHandler().scrollItem(chatId, query, ScrollState.NEXT);
    }
    //POOL End


    public List<PartialBotApiMethod<?>> handleFitnessCurrentScheduleButton(String chatId, CallbackQuery query) {
        return handlersFacade.getFitnessButtonHandler().getFitnessSchedulesButtonHandler().handle(chatId, query);
    }

    //Changes Start
    public List<PartialBotApiMethod<?>> handleFitnessChangesButton(String chatId, CallbackQuery query) {
        return handlersFacade.getFitnessButtonHandler().getFitnessChangesButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleFitnessChangePrevButton(String chatId, CallbackQuery query) {
        return handlersFacade.getFitnessButtonHandler().getFitnessChangesButtonHandler().previousChange(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleFitnessChangeNextButton(String chatId, CallbackQuery query) {
        return handlersFacade.getFitnessButtonHandler().getFitnessChangesButtonHandler().nextChange(chatId, query);
    }
    //Changes End

    //Fitness menu end


    //Spa menu start
    public List<PartialBotApiMethod<?>> handleSpaButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleSpaSpecialistsButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaButtonHandler().openSpecialistsMenu(chatId, query);
    }

    //Spa sales start
    public List<PartialBotApiMethod<?>> handleSpaSalesButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaButtonHandler().getSpaSalesButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleSpaSalesPrevButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaButtonHandler().getSpaSalesButtonHandler().previousSale(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleSpaSalesNextButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaButtonHandler().getSpaSalesButtonHandler().nextSale(chatId, query);
    }
    //Spa sales end

    //Nails start
    public List<PartialBotApiMethod<?>> handleSpaNailsButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaButtonHandler().getSpaNailsButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleSpaNailsPrevButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaButtonHandler().getSpaNailsButtonHandler().previousSpecialist(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleSpaNailsNextButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaButtonHandler().getSpaNailsButtonHandler().nextSpecialist(chatId, query);
    }
    //Nails end

    //Massage start
    public List<PartialBotApiMethod<?>> handleSpaMassageButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaButtonHandler().getSpaMassageButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleSpaMassagePrevButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaButtonHandler().getSpaMassageButtonHandler().previousSpecialist(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleSpaMassageNextButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaButtonHandler().getSpaMassageButtonHandler().nextSpecialist(chatId, query);
    }
    //Massage end

    //Cosmetology start
    public List<PartialBotApiMethod<?>> handleSpaCosmetologyButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaButtonHandler().getSpaCosmetologyButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleSpaCosmetologyPrevButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaButtonHandler().getSpaCosmetologyButtonHandler().previousSpecialist(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleSpaCosmetologyNextButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaButtonHandler().getSpaCosmetologyButtonHandler().nextSpecialist(chatId, query);
    }
    //Cosmetology end

    //Stylists start
    public List<PartialBotApiMethod<?>> handleSpaStylistsButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaButtonHandler().getSpaStylistsButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleSpaStylistsPrevButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaButtonHandler().getSpaStylistsButtonHandler().previousSpecialist(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleSpaStylistsNextButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaButtonHandler().getSpaStylistsButtonHandler().nextSpecialist(chatId, query);
    }
    //Stylists end

    //handleSpaCosmetologyButton
    //handleSpaStylistsButton

    //Spa menu end

    public List<PartialBotApiMethod<?>> handleContactUsButton(String chatId, CallbackQuery query) {
        return handlersFacade.getCallManagerButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleReturnMainMenuButton(String chatId, CallbackQuery query) {
        return Utils.moveToMainMenu(chatId, query.getMessage().getMessageId());
    }

    @Autowired
    public void setHandlersFacade(HandlersFacade handlersFacade) {
        this.handlersFacade = handlersFacade;
    }
}

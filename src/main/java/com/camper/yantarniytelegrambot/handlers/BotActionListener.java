package com.camper.yantarniytelegrambot.handlers;


import com.camper.yantarniytelegrambot.enums.ScrollState;
import com.camper.yantarniytelegrambot.utils.Utils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;

@Service
public class BotActionListener {
    private HandlersFacade handlersFacade;

    public List<PartialBotApiMethod<?>> handleClubCardButton(String chatId, CallbackQuery query) {
        return handlersFacade.getClubCardButtonHandler().handle(chatId, query);
    }

    //Card types menu Start
    public List<PartialBotApiMethod<?>> handleClubCardsTypesButton(String chatId, CallbackQuery query) {
        return handlersFacade.getClubCardTypeButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleIndGoldClubCardButton(String chatId, CallbackQuery query) {
        return handlersFacade.getClubCardTypeButtonHandler().individualGoldCard(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleDayGoldClubCardButton(String chatId, CallbackQuery query) {
        return handlersFacade.getClubCardTypeButtonHandler().dayGoldCard(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleWeekendClubCardButton(String chatId, CallbackQuery query) {
        return handlersFacade.getClubCardTypeButtonHandler().weekendCard(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handlePoolClubCardButton(String chatId, CallbackQuery query) {
        return handlersFacade.getClubCardTypeButtonHandler().poolCard(chatId, query);
    }

    //Card types menu End

    public List<PartialBotApiMethod<?>> handleClubCardsSalesContactUsButton(String chatId, CallbackQuery query) {
        return handlersFacade.getCcSalesContactUsButtonHandler().handle(chatId, query);
    }

    //Sales menu start
    public List<PartialBotApiMethod<?>> handleClubCardsSalesButton(String chatId, CallbackQuery query) {
        return handlersFacade.getClubCardSalesButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleClubCardsSalesPrevButton(String chatId, CallbackQuery query) {
        return handlersFacade.getClubCardSalesButtonHandler().scrollItem(chatId, query, ScrollState.PREVIOUS);
    }

    public List<PartialBotApiMethod<?>> handleClubCardsSalesNextButton(String chatId, CallbackQuery query) {
        return handlersFacade.getClubCardSalesButtonHandler().scrollItem(chatId, query, ScrollState.NEXT);
    }

    public List<PartialBotApiMethod<?>> handleClubCardsSalesReturnButton(String chatId, CallbackQuery query) {
        return handlersFacade.getClubCardSalesButtonHandler().returnToMainMenu(chatId, query);
    }
    //Sales menu end

    //Fitness menu start

    public List<PartialBotApiMethod<?>> handleFitnessButton(String chatId, CallbackQuery query) {
        return handlersFacade.getFitnessButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleFitnessContactUsButton(String chatId, CallbackQuery query) {
        return handlersFacade.getFitnessButtonHandler().getFitnessContactUsButtonHandler().handle(chatId, query);
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


    //Schedules start
    public List<PartialBotApiMethod<?>> handleSchedulesMenuButton(String chatId, CallbackQuery query) {
        return handlersFacade.getScheduleMenuButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleFitnessCurrentScheduleButton(String chatId, CallbackQuery query) {
        return handlersFacade.getScheduleMenuButtonHandler().getSchedulesButtonHandler().handle(chatId, query);
    }

    @SneakyThrows
    public List<PartialBotApiMethod<?>> handleSchedulePrevButton(String chatId, CallbackQuery query) {
        return handlersFacade.getScheduleMenuButtonHandler().getSchedulesButtonHandler().scrollSchedule(chatId, query, ScrollState.PREVIOUS);
    }

    @SneakyThrows
    public List<PartialBotApiMethod<?>> handleScheduleNextButton(String chatId, CallbackQuery query) {
        return handlersFacade.getScheduleMenuButtonHandler().getSchedulesButtonHandler().scrollSchedule(chatId, query, ScrollState.NEXT);
    }

    //Changes Start
    public List<PartialBotApiMethod<?>> handleFitnessChangesButton(String chatId, CallbackQuery query) {
        return handlersFacade.getScheduleMenuButtonHandler().getScheduleChangesButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleFitnessChangePrevButton(String chatId, CallbackQuery query) {
        return handlersFacade.getScheduleMenuButtonHandler().getScheduleChangesButtonHandler().scrollChange(chatId, query, ScrollState.PREVIOUS);
    }

    public List<PartialBotApiMethod<?>> handleFitnessChangeNextButton(String chatId, CallbackQuery query) {
        return handlersFacade.getScheduleMenuButtonHandler().getScheduleChangesButtonHandler().scrollChange(chatId, query, ScrollState.NEXT);
    }
    //Changes End

    //Schedules end




    //Fitness menu end


    //Spa menu start
    public List<PartialBotApiMethod<?>> handleSpaButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleSpaSpecialistsButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaButtonHandler().openSpecialistsMenu(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleSpaContactUsButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaButtonHandler().getSpaSpecContactUsButtonHandler().handle(chatId, query);
    }

    //Spa sales start
    public List<PartialBotApiMethod<?>> handleSpaSalesButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaButtonHandler().getSpaSalesButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleSpaSalesPrevButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaButtonHandler().getSpaSalesButtonHandler().scrollItem(chatId, query, ScrollState.PREVIOUS);
    }

    public List<PartialBotApiMethod<?>> handleSpaSalesNextButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaButtonHandler().getSpaSalesButtonHandler().scrollItem(chatId, query, ScrollState.NEXT);
    }

    public List<PartialBotApiMethod<?>> handleSpaSalesContactUsButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaButtonHandler().getSpaSalesContactUsButton().handle(chatId, query);
    }
    //Spa sales end

    //Nails start
    public List<PartialBotApiMethod<?>> handleSpaNailsButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaButtonHandler().getSpaNailsButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleSpaNailsPrevButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaButtonHandler().getSpaNailsButtonHandler().scrollItem(chatId, query, ScrollState.PREVIOUS);
    }

    public List<PartialBotApiMethod<?>> handleSpaNailsNextButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaButtonHandler().getSpaNailsButtonHandler().scrollItem(chatId, query, ScrollState.NEXT);
    }
    //Nails end

    //Massage start
    public List<PartialBotApiMethod<?>> handleSpaMassageButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaButtonHandler().getSpaMassageButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleSpaMassagePrevButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaButtonHandler().getSpaMassageButtonHandler().scrollItem(chatId, query, ScrollState.PREVIOUS);
    }

    public List<PartialBotApiMethod<?>> handleSpaMassageNextButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaButtonHandler().getSpaMassageButtonHandler().scrollItem(chatId, query, ScrollState.NEXT);
    }
    //Massage end

    //Cosmetology start
    public List<PartialBotApiMethod<?>> handleSpaCosmetologyButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaButtonHandler().getSpaCosmetologyButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleSpaCosmetologyPrevButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaButtonHandler().getSpaCosmetologyButtonHandler().scrollItem(chatId, query, ScrollState.PREVIOUS);
    }

    public List<PartialBotApiMethod<?>> handleSpaCosmetologyNextButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaButtonHandler().getSpaCosmetologyButtonHandler().scrollItem(chatId, query, ScrollState.NEXT);
    }
    //Cosmetology end

    //Stylists start
    public List<PartialBotApiMethod<?>> handleSpaStylistsButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaButtonHandler().getSpaStylistsButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleSpaStylistsPrevButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaButtonHandler().getSpaStylistsButtonHandler().scrollItem(chatId, query, ScrollState.PREVIOUS);
    }

    public List<PartialBotApiMethod<?>> handleSpaStylistsNextButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaButtonHandler().getSpaStylistsButtonHandler().scrollItem(chatId, query, ScrollState.NEXT);
    }
    //Stylists end


    //Spa menu end

    public List<PartialBotApiMethod<?>> handleContactUsButton(String chatId, CallbackQuery query) {
        return handlersFacade.getCallManagerButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleQrSberButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSberQRButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleReturnMainMenuButton(String chatId, CallbackQuery query) {
        return Utils.moveToMainMenu(chatId, query.getMessage().getMessageId());
    }

    @Autowired
    public void setHandlersFacade(HandlersFacade handlersFacade) {
        this.handlersFacade = handlersFacade;
    }
}

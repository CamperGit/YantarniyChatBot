package com.camper.yantarniytelegrambot.handlers;


import com.camper.yantarniytelegrambot.entity.UserEntity;
import com.camper.yantarniytelegrambot.enums.ScrollState;
import com.camper.yantarniytelegrambot.services.UserEntityService;
import com.camper.yantarniytelegrambot.utils.Utils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.sql.Timestamp;
import java.util.List;

@Service
public class BotActionListener {
    private HandlersFacade handlersFacade;
    private UserEntityService userEntityService;

    //Club cards menu start
    public List<PartialBotApiMethod<?>> handleClubCardButton(String chatId, CallbackQuery query) {
        return handlersFacade.getClubCardButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleIndGoldClubCardButton(String chatId, CallbackQuery query) {
        return handlersFacade.getClubCardButtonHandler().individualGoldCard(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleDayGoldClubCardButton(String chatId, CallbackQuery query) {
        return handlersFacade.getClubCardButtonHandler().dayGoldCard(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleWeekendClubCardButton(String chatId, CallbackQuery query) {
        return handlersFacade.getClubCardButtonHandler().weekendCard(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handlePoolClubCardButton(String chatId, CallbackQuery query) {
        return handlersFacade.getClubCardButtonHandler().poolCard(chatId, query);
    }
    //Card types menu End


    //Sales menu start
    public List<PartialBotApiMethod<?>> handleSalesButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSalesMenuButtonHandler().handle(chatId, query);
    }

    //Club cards sales menu start
    public List<PartialBotApiMethod<?>> handleClubCardsSalesContactUsButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSalesMenuButtonHandler().getCcSalesContactUsButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleClubCardsSalesButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSalesMenuButtonHandler().getClubCardSalesButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleClubCardsSalesPrevButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSalesMenuButtonHandler().getClubCardSalesButtonHandler().scrollItem(chatId, query, ScrollState.PREVIOUS);
    }

    public List<PartialBotApiMethod<?>> handleClubCardsSalesNextButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSalesMenuButtonHandler().getClubCardSalesButtonHandler().scrollItem(chatId, query, ScrollState.NEXT);
    }
    //Club cards sales menu end

    //Spa sales start
    public List<PartialBotApiMethod<?>> handleSpaSalesButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSalesMenuButtonHandler().getSpaSalesButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleSpaSalesPrevButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSalesMenuButtonHandler().getSpaSalesButtonHandler().scrollItem(chatId, query, ScrollState.PREVIOUS);
    }

    public List<PartialBotApiMethod<?>> handleSpaSalesNextButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSalesMenuButtonHandler().getSpaSalesButtonHandler().scrollItem(chatId, query, ScrollState.NEXT);
    }

    public List<PartialBotApiMethod<?>> handleSpaSalesContactUsButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSalesMenuButtonHandler().getSpaSalesContactUsButtonHandler().handle(chatId, query);
    }
    //Spa sales end

    //Sales menu end


    //Employees menu start

    public List<PartialBotApiMethod<?>> handleEmployeeMenuButton(String chatId, CallbackQuery query) {
        return handlersFacade.getEmployeeMenuButtonHandler().handle(chatId, query);
    }

        //Spa menu start
        public List<PartialBotApiMethod<?>> handleSpaSpecialistsButton(String chatId, CallbackQuery query) {
            return handlersFacade.getSpaButtonHandler().handle(chatId, query);
        }

        public List<PartialBotApiMethod<?>> handleSpaContactUsButton(String chatId, CallbackQuery query) {
            return handlersFacade.getSpaButtonHandler().getSpaSpecContactUsButtonHandler().handle(chatId, query);
        }

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


        //Fitness menu end


    //Employees menu end


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

    //SpaService menu start

    public List<PartialBotApiMethod<?>> handleSpaServiceMenuButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaServiceMenuButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleSSContactUsButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaServiceMenuButtonHandler().getSSContactUsButtonHandler().handle(chatId, query);
    }

    //Nails serv
    public List<PartialBotApiMethod<?>> handleSSNailsButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaServiceMenuButtonHandler().getNailsSSButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleSSNailsPricePrevButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaServiceMenuButtonHandler().getNailsSSButtonHandler().scrollPrice(chatId, query, ScrollState.PREVIOUS);
    }

    public List<PartialBotApiMethod<?>> handleSSNailsPriceNextButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaServiceMenuButtonHandler().getNailsSSButtonHandler().scrollPrice(chatId, query,ScrollState.NEXT);
    }
    //Nails serv
    //Cosmetology serv
    public List<PartialBotApiMethod<?>> handleSSCosmetologyButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaServiceMenuButtonHandler().getCosmetologySSButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleSSCosmetologyPricePrevButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaServiceMenuButtonHandler().getCosmetologySSButtonHandler().scrollPrice(chatId, query, ScrollState.PREVIOUS);
    }

    public List<PartialBotApiMethod<?>> handleSSCosmetologyPriceNextButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaServiceMenuButtonHandler().getCosmetologySSButtonHandler().scrollPrice(chatId, query,ScrollState.NEXT);
    }
    //Cosmetology serv
    //Stylists serv
    public List<PartialBotApiMethod<?>> handleSSStylistsButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaServiceMenuButtonHandler().getStylistsSSButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleSSStylistsPricePrevButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaServiceMenuButtonHandler().getStylistsSSButtonHandler().scrollPrice(chatId, query, ScrollState.PREVIOUS);
    }

    public List<PartialBotApiMethod<?>> handleSSStylistsPriceNextButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaServiceMenuButtonHandler().getStylistsSSButtonHandler().scrollPrice(chatId, query,ScrollState.NEXT);
    }
    //Stylists serv
    //Massage serv
    public List<PartialBotApiMethod<?>> handleSSMassageButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaServiceMenuButtonHandler().getMassageSSButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleSSMassagePricePrevButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaServiceMenuButtonHandler().getMassageSSButtonHandler().scrollPrice(chatId, query, ScrollState.PREVIOUS);
    }

    public List<PartialBotApiMethod<?>> handleSSMassagePriceNextButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaServiceMenuButtonHandler().getMassageSSButtonHandler().scrollPrice(chatId, query,ScrollState.NEXT);
    }
    //Massage serv
    //Bathhouse serv
    public List<PartialBotApiMethod<?>> handleSSBathhouseButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaServiceMenuButtonHandler().getBathhouseSSButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleSSBathhousePricePrevButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaServiceMenuButtonHandler().getBathhouseSSButtonHandler().scrollPrice(chatId, query, ScrollState.PREVIOUS);
    }

    public List<PartialBotApiMethod<?>> handleSSBathhousePriceNextButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSpaServiceMenuButtonHandler().getBathhouseSSButtonHandler().scrollPrice(chatId, query,ScrollState.NEXT);
    }
    //Bathhouse serv
    //SpaService menu end

    public List<PartialBotApiMethod<?>> handleContactManagerButton(String chatId, CallbackQuery query) {
        return handlersFacade.getCallManagerButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleContactAdminButton(String chatId, CallbackQuery query) {
        return handlersFacade.getCallAdminButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleQrSberButton(String chatId, CallbackQuery query) {
        return handlersFacade.getSberQRButtonHandler().handle(chatId, query);
    }

    public List<PartialBotApiMethod<?>> handleReturnMainMenuButton(String chatId, CallbackQuery query) {
        UserEntity user = userEntityService.findUserByChatId(chatId);
        user.setLastEntry(new Timestamp(System.currentTimeMillis()));
        //userEntityService.saveUser(user);
        return Utils.moveToMainMenu(chatId, query.getMessage().getMessageId());
    }

    @Autowired
    public void setHandlersFacade(HandlersFacade handlersFacade) {
        this.handlersFacade = handlersFacade;
    }

    @Autowired
    public void setUserEntityService(UserEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }
}

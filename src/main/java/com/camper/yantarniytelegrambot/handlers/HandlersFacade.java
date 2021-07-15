package com.camper.yantarniytelegrambot.handlers;

import com.camper.yantarniytelegrambot.handlers.ClubCards.ClubCardButtonHandler;
import com.camper.yantarniytelegrambot.handlers.Employees.EmployeeMenuButtonHandler;
import com.camper.yantarniytelegrambot.handlers.Fitness.FitnessButtonHandler;
import com.camper.yantarniytelegrambot.handlers.Sales.SalesMenuButtonHandler;
import com.camper.yantarniytelegrambot.handlers.Schedule.ScheduleMenuButtonHandler;
import com.camper.yantarniytelegrambot.handlers.Spa.SpaButtonHandler;
import com.camper.yantarniytelegrambot.handlers.SpaService.SpaServiceMenuButtonHandler;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Service
public class HandlersFacade {
    private final SalesMenuButtonHandler salesMenuButtonHandler;
    private final EmployeeMenuButtonHandler employeeMenuButtonHandler;
    private final ClubCardButtonHandler clubCardButtonHandler;
    private final FitnessButtonHandler fitnessButtonHandler;
    private final ScheduleMenuButtonHandler scheduleMenuButtonHandler;
    private final SpaButtonHandler spaButtonHandler;
    private final CallManagerButtonHandler callManagerButtonHandler;
    private final SberQRButtonHandler sberQRButtonHandler;
    private final SpaServiceMenuButtonHandler spaServiceMenuButtonHandler;

    @Autowired

    public HandlersFacade(SalesMenuButtonHandler salesMenuButtonHandler, EmployeeMenuButtonHandler employeeMenuButtonHandler,
                          ClubCardButtonHandler clubCardButtonHandler, FitnessButtonHandler fitnessButtonHandler,
                          ScheduleMenuButtonHandler scheduleMenuButtonHandler, SpaButtonHandler spaButtonHandler,
                          CallManagerButtonHandler callManagerButtonHandler, SberQRButtonHandler sberQRButtonHandler,
                          SpaServiceMenuButtonHandler spaServiceMenuButtonHandler) {
        this.salesMenuButtonHandler = salesMenuButtonHandler;
        this.employeeMenuButtonHandler = employeeMenuButtonHandler;
        this.clubCardButtonHandler = clubCardButtonHandler;
        this.fitnessButtonHandler = fitnessButtonHandler;
        this.scheduleMenuButtonHandler = scheduleMenuButtonHandler;
        this.spaButtonHandler = spaButtonHandler;
        this.callManagerButtonHandler = callManagerButtonHandler;
        this.sberQRButtonHandler = sberQRButtonHandler;
        this.spaServiceMenuButtonHandler = spaServiceMenuButtonHandler;
    }
}

package com.camper.yantarniytelegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class YantarniyTelegramBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(YantarniyTelegramBotApplication.class, args);
    }

}

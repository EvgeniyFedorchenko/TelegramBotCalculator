package com.evgeniyfedorchenko1.telegrambotcalculator;

import com.evgeniyfedorchenko1.telegrambotcalculator.telegramapi.TelegramBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class TelegramBotCalculatorApplication {
    public static void main(String[] args) {
        SpringApplication.run(TelegramBotCalculatorApplication.class, args);
    }

    @Bean
    TelegramBotsApi telegramBotsApi(TelegramBot telegramBot) throws TelegramApiException {

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(telegramBot);
        return telegramBotsApi;
    }
}

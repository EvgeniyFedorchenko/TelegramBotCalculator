package com.evgeniyfedorchenko.telegrambotcalculator.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    @Value("${bot.name}")
    private String botName;

    public TelegramBot(@Value("${bot.token}") String botToken) {
        super(botToken);
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public void onUpdateReceived(Update update) {

        String inText = null;
        long chatId = 0;
        if (update.hasMessage() && update.getMessage().hasText()) {
            inText = update.getMessage().getText();
            chatId = update.getMessage().getChatId();
        }

        SendMessage outMess = new SendMessage();
        String outText = inText;

        outMess.setChatId(chatId);
        outMess.setText(outText);
        try {
            execute(outMess);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}

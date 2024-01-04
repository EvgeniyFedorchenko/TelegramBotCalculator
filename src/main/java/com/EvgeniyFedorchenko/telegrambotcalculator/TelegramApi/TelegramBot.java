package com.evgeniyfedorchenko.telegrambotcalculator.telegramapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

import static com.evgeniyfedorchenko.telegrambotcalculator.telegramapi.GenerateAnswer.parseMessage;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    @Value("${bot.name}")
    private String botName;
    private final List<BotCommand> commands = new ArrayList<>(List.of(
            new BotCommand("/start", "Get a welcome message"),
            new BotCommand("/help", "See, what can this bot")));

    public TelegramBot(@Value("${bot.token}") String botToken) {
        super(botToken);
        try {
            this.execute(new SetMyCommands(commands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            System.out.println("Failed to add buttons");
        }
    }

    @Override
    public String getBotUsername() {
        return this.botName;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {

            SendMessage outMess = new SendMessage();
            outMess.setChatId(update.getMessage().getChatId());
            outMess.setText(parseMessage(update.getMessage()));

            try {
                execute(outMess);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

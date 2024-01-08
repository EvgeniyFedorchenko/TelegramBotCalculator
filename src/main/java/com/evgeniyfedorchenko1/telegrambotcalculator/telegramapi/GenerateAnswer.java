package com.evgeniyfedorchenko1.telegrambotcalculator.telegramapi;

import com.evgeniyfedorchenko1.telegrambotcalculator.exceptions.BracketsCountException;
import com.evgeniyfedorchenko1.telegrambotcalculator.exceptions.UncorrectedRomanOperand;
import com.evgeniyfedorchenko1.telegrambotcalculator.logic.GeneralLogic;
import org.telegram.telegrambots.meta.api.objects.Message;

public class GenerateAnswer {

    // TODO: 30.12.2023 Возможно стоит разделить функционал на ConvertMode и CalculateMode
    //       И назначать их по кнопкам в меню. Если юзер в неправильном режиме ввел выражение, то предлагать
    //       переключиться на другой режим по всплывающим кнопкам
    private static final String UNCORRECTED_ROMAN_OPERAND_EXCEPTION_ANSWER =
            "Oops!\nRoman operand \"%s\" is uncorrected\n\nMaybe you want to read a hint on how to compose Roman numbers";
    private static final String BRACKETS_COUNT_EXCEPTION_ANSWER =
            "Oops!\nSeems like the amount of opening and closing brackets is not equal";

    static String parseMessage(Message message) {

        try {
            return switch (message.getText()) {
                case "/start" -> startCommand(message.getChat().getFirstName());
                case "/help" -> helpCommand();
                default -> sendToCalculating(message.getText());
            };
        } catch (UncorrectedRomanOperand e) {
            return UNCORRECTED_ROMAN_OPERAND_EXCEPTION_ANSWER.formatted(e.getRomanOperand());
        } catch (BracketsCountException e) {
            return BRACKETS_COUNT_EXCEPTION_ANSWER;
        }
    }

    private static String sendToCalculating(String message) throws BracketsCountException {
        return (firstValidate(message)) ? GeneralLogic.mainCalc(message) :
                "Sorry, but there’s no such command or you have entered an unsupported character";
    }

    private static boolean firstValidate(String message) {
        return message.toUpperCase().matches("^[\\sIVXLCDMN0-9()!^√*/÷:+-]+$");
    }

    private static String startCommand(String firstName) {
        String version = "2.0 Refresh";
        return """
                Hello, %s
                                
                                
                This is TelegramBotCalculator, version %s, written on Java 21, with Spring Framework 3.2.2-SNAPSHOT
                                
                                
                Use /help, to see that he can
                """.formatted(firstName, version);
    }

    private static String helpCommand() {
        return """
                Available actions: х  ÷  +  -  √  ^  !  ( )
                                
                                
                Amount of operands is ≈1bln
                Range of some roman operand is 3999 (more/less than zero)
                Range of some arabian operand is ≈9 quintillion (more/less than zero)
                                
                                
                You can enter the expression with Roman and Arabian operands at the same time
                If you just want to convert a number, enter this number without any sign
                Perception of an expression insensitive to case and space
                                
                                
                Thank you!
                """;
    }
}

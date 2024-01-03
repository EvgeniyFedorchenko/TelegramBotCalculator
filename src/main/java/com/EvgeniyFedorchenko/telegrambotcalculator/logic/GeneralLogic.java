package com.evgeniyfedorchenko.telegrambotcalculator.logic;

import com.evgeniyfedorchenko.telegrambotcalculator.exceptions.UncorrectedRomanOperand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GeneralLogic {

    public static String mainCalc(String expression) {

        List<String> listOfInput = bringToStandard(expression);

        // Разделение режимов

        if (!validateBrackets(listOfInput)) {
            return "Oops!\nSeems like the amount of opening and closing brackets is not equal";
        }


        boolean romanOperandsArePresent;
        for (int i = 0; i < listOfInput.size(); i++) {
            if (checkRoman(listOfInput.get(i))) {
                romanOperandsArePresent = true;
                try {
                    listOfInput.set(i, Convert.ConvertingToArabNum(listOfInput.get(i)));
                } catch (UncorrectedRomanOperand e) {
                    // TODO: 03.01.2024 Добавить кнопку под сообщением "Help message" с инфо про составление римских чисел
                    return "Oops!\n" + e.getMessage() + "Maybe you want to read a hint on how to compose Roman numbers";
                }
            }
        }


        while (listOfInput.contains("(")) {
            List<String> deepestExpression = searchDeepestExpression(listOfInput);
            // Считаем, пересобираем
        }


        // return answer from mainCalc()
        return null;
    }

    private static List<String> searchDeepestExpression(List<String> listOfInput) {

        int innermostOpenBracketPosition = 0;

        for (int i = 0; i < listOfInput.size(); i++) {
            if (listOfInput.get(i).equals("(")) {
                innermostOpenBracketPosition = i;
            }
            if (listOfInput.get(i).equals(")")) {
                List<String> deepestExpression = listOfInput.subList(innermostOpenBracketPosition + 1, i);
                return new ArrayList<>(deepestExpression);
            }
        }
        return null;
    }

    private static boolean checkRoman(List<String> listOfInput) {

        List<String> romanNumbers = Arrays.asList("I", "V", "X", "L", "C", "D", "M", "N");

        for (String operand : listOfInput) {
            if (romanNumbers.contains(operand.split("")[0])) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkRoman(String operand) {

        List<String> romanNumbers = Arrays.asList("I", "V", "X", "L", "C", "D", "M", "N");

        for (String s : operand.split("")) {
            if (romanNumbers.contains(operand.split("")[0])) {
                return true;
            }
        }
        return false;
    }

    private static List<String> bringToStandard(String expression) {

        String signs = "()!^√*/+-";
        return Arrays.asList(expression.toUpperCase()

                .replace(" ", "")
                .replace("÷", "/")
                .replace(":", "/")

                .split("(?<=[" + signs + "])|(?=[" + signs + "])"));
    }

    private static boolean validateBrackets(List<String> listOfInput) {

        Map<String, Long> brackets = listOfInput.stream()
                .filter(s -> s.equals(")") || s.equals("("))
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

        return brackets.get("(").equals(brackets.get(")"));
    }
}

package com.evgeniyfedorchenko.telegrambotcalculator.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNumeric;

public class GeneralLogic {

    public static boolean romanOperandsArePresent = false;

    public static String mainCalc(String expression) {

        List<String> listOfInput = bringToStandard(expression);

        // Разделение режимов

        if (!validateBrackets(listOfInput)) {
            return "Oops!\nSeems like the amount of opening and closing brackets is not equal";
        }


        for (String s : listOfInput) {
            if (checkRomanBool(s)) {
                String convertedOperand = convertRoman(s);
                if (!isNumeric(convertedOperand)) {
                    return convertedOperand;
                } else {
                    listOfInput.set(listOfInput.indexOf(s), convertedOperand);
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

    private static boolean checkRomanBool(String s) {
        List<String> romanNumbers = Arrays.asList("I", "V", "X", "L", "C", "D", "M", "N");
        return romanNumbers.contains(s.split("")[0]);
    }

    private static String convertRoman(String operand) {

        romanOperandsArePresent = true;
        if (!isNumeric(Convert.ConvertingToArabNum(operand))) {
            return "Oops!\nRoman operand \"" + operand + "\" is uncorrected\n\nMaybe you want to read a hint on how to compose Roman numbers";
        }
        return operand;
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

        if (!brackets.containsKey("(")) brackets.put("(", 0L);
        if (!brackets.containsKey(")")) brackets.put(")", 0L);

        return brackets.get("(").equals(brackets.get(")"));
    }
}

package com.evgeniyfedorchenko.telegrambotcalculator.logic;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.evgeniyfedorchenko.telegrambotcalculator.logic.RomanNumbersUtils.*;

public class GeneralLogic {

    public static boolean romanOperandsArePresent = false;

    public static String mainCalc(String expression) {


        List<String> listOfInput = bringToStandard(expression);

        // Разделение режимов

        if (!validateBrackets(listOfInput)) {
            return "Oops!\nSeems like the amount of opening and closing brackets is not equal";
        }


        for (String s : listOfInput) {
            boolean isRoman = checkRoman(s);
            if (isRoman && !checkCorrectRoman(s)) {
                return "Oops!\nRoman operand \"" + s + "\" is uncorrected\n\nMaybe you want to read a hint on how to compose Roman numbers";
            } else if (isRoman) {
                romanOperandsArePresent = true;
                listOfInput.set(listOfInput.indexOf(s), ConvertingToArabNum(s));
            }
        }
        /*if (listOfInput.stream().anyMatch(s -> checkRoman(s) && !checkCorrectRoman(s))) {
            return "Oops!\nRoman operand \"\" is uncorrected\n\nMaybe you want to read a hint on how to compose Roman numbers";
        }
        listOfInput.stream()
                .filter(RomanNumbersUtils::checkRoman)
                .forEach(s -> {
                    romanOperandsArePresent = true;
                    listOfInput.set(listOfInput.indexOf(s), ConvertingToArabNum(s));
                });*/


        while (listOfInput.contains("(")) {
            List<String> deepestExpression = searchDeepestExpression(listOfInput);
            // Считаем, пересобираем
        }


        // return answer from mainCalc()
        return null;
    }


    private static List<String> searchDeepestExpression(List<String> listOfInput) {

        /*int innermostOpenBracketPosition = 0;

        for (int i = 0; i < listOfInput.size(); i++) {
            if (listOfInput.get(i).equals("(")) {
                innermostOpenBracketPosition = i;
            }
            if (listOfInput.get(i).equals(")")) {
                List<String> deepestExpression = listOfInput.subList(innermostOpenBracketPosition + 1, i);
                return new ArrayList<>(deepestExpression);
            }
        }*/
        int innermostOpenBracketPosition = IntStream.range(0, listOfInput.size())
                .filter(i -> listOfInput.get(i).equals("("))
                .reduce((first, second) -> second).orElse(0);

        return listOfInput.subList(innermostOpenBracketPosition,
                IntStream.range(innermostOpenBracketPosition, listOfInput.size())
                .filter(i -> listOfInput.get(i).equals(")"))
                .findFirst().orElse(0));
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

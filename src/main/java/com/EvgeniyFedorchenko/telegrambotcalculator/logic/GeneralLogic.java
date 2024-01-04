package com.evgeniyfedorchenko.telegrambotcalculator.logic;

import com.evgeniyfedorchenko.telegrambotcalculator.exceptions.BracketsCountException;
import com.evgeniyfedorchenko.telegrambotcalculator.exceptions.UncorrectedRomanOperand;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.evgeniyfedorchenko.telegrambotcalculator.logic.RomanNumeralsUtils.ConvertingToArabNum;

public class GeneralLogic {

    public static boolean romanOperandsArePresent = false;


    public static String mainCalc(String expression) throws UncorrectedRomanOperand, BracketsCountException {


        List<String> listOfInput = bringToStandard(expression);


        // Разделение режимов


        /*for (String s : listOfInput) {
            if (checkRoman(s)) {
                listOfInput.set(listOfInput.indexOf(s), ConvertingToArabNum(s));
            }
        }*/
        listOfInput.stream()
                .filter(RomanNumeralsUtils::checkRoman)
                .forEach(s -> listOfInput.set(listOfInput.indexOf(s), ConvertingToArabNum(s)));


        while (listOfInput.contains("(")) {
            if (!validateBrackets(listOfInput)) {
                throw new BracketsCountException("Count of opening and closing brackets is not equal in expression: " + expression);
            }
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
        int innerOpenBracketPosition = IntStream.range(0, listOfInput.size())
                .filter(i -> listOfInput.get(i).equals("("))
                .reduce((first, second) -> second).orElse(0);

        int innerCloseBracketPosition = IntStream.range(innerOpenBracketPosition, listOfInput.size())
                .filter(i -> listOfInput.get(i).equals(")"))
                .findFirst().orElse(0);

        return listOfInput.subList(innerOpenBracketPosition, innerCloseBracketPosition);
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

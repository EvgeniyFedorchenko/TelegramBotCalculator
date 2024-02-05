package com.evgeniyfedorchenko.telegrambotcalculator.logic;

import com.evgeniyfedorchenko.telegrambotcalculator.exceptions.BracketsCountException;

import java.util.*;
import java.util.stream.Collectors;

import static com.evgeniyfedorchenko.telegrambotcalculator.logic.RomanNumeralsUtils.convertingToArabNum;

public class GeneralLogic {

    public static boolean romanOperandsArePresent = false;
    private List<String> listOfInput;


    public String mainCalc(String input) {

        // Create object
        listOfInput = bringToStandard(input);


        // Разделение режимов


        // Validation roman numbers and convert them
        listOfInput.stream()
                .filter(RomanNumeralsUtils::liteRomanChecker)
                .forEach(this::convert);

        // Search deepest expression
        if (!validateBrackets(listOfInput)) {
            throw new BracketsCountException("Opening/closing brackets count isn't equal in expression: " + input);
        }

        SimpleCalculator simpleCalculator = new SimpleCalculator();
        return simplifyExpression(simpleCalculator);
    }

    private String simplifyExpression(SimpleCalculator simpleCalculator) {

        if (listOfInput.contains("(") || listOfInput.size() > 1) {
            List<String> deepestExpression = searchDeepestExpression(listOfInput);   // вместе со скобками
            String interimResult = simpleCalculator.calculateExpression(new ArrayList<>(deepestExpression));

            int startIndex = listOfInput.indexOf(deepestExpression.get(0));
            int endIndex = listOfInput.indexOf(deepestExpression.get(deepestExpression.size() - 1));
            listOfInput.add(startIndex, interimResult);
            listOfInput.subList(startIndex + 1, endIndex + 2).clear();

            return simplifyExpression(simpleCalculator);

        } else {
            return listOfInput.get(0);
        }
    }

    private void convert(String s) {
        listOfInput.set(listOfInput.indexOf(s), convertingToArabNum(s));
    }

    private static List<String> searchDeepestExpression(List<String> listOfInput) {

        int innermostOpenBracketPosition = 0;
        for (int i = 0; i < listOfInput.size(); i++) {

            if (listOfInput.get(i).equals("(")) {
                innermostOpenBracketPosition = i;
            } else if (listOfInput.get(i).equals(")")) {
                return new ArrayList<>(listOfInput.subList(innermostOpenBracketPosition, i + 1));   // со скобками
            }
        }
        return listOfInput;
    }

    private static List<String> bringToStandard(String expression) {

        List<String> standardList = new ArrayList<>(Arrays.asList(expression.toUpperCase()
                .replaceAll(",", ".")
                .replaceAll(" ", "")
                .replaceAll("÷", "/")
                .replaceAll(":", "/")
                .split("(?<=[()!^√*/+-])|(?=[()!^√*/+-])")));

        /* Данные вставки нужны, чтобы работать с выражением по общим правилам,
           где 3 составляющих: два операнда и знак действия (см. SimpleCalculator.calculating) */
        for (int i = 0; i < standardList.size(); i++) {
            if (standardList.get(i).equals("√") && !standardList.get(i - 1).matches("^[+-]?(\\d*\\.)?\\d+$")) {
                standardList.add(i, "2");
            }
            if (standardList.get(i).equals("!")) {
                standardList.add(i + 1, "factorial");
            }
        }
        return standardList;
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

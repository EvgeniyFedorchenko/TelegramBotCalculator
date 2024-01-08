package com.evgeniyfedorchenko.telegrambotcalculator.logic;

import com.evgeniyfedorchenko.telegrambotcalculator.exceptions.UncorrectedRomanOperand;

import java.util.*;

public class RomanNumeralsUtils {

    private static final Map<String, Integer> CONVERT_MAP = new LinkedHashMap<>(8) {{
        put("I", 1);
        put("X", 10);
        put("C", 100);
        put("M", 1000);
        put("V", 5);
        put("L", 50);
        put("D", 500);
        put("N", 0);
    }};


    public static boolean liteRomanChecker(String operand) throws UncorrectedRomanOperand {

        boolean isRoman = CONVERT_MAP.containsKey(operand.split("")[0]);

        if (isRoman && !deepRomanChecker(operand)) {
            throw new UncorrectedRomanOperand("Uncorrected roman operand: ", operand);
        } else if (isRoman) {
            GeneralLogic.romanOperandsArePresent = true;
            return true;
        }
        return false;
    }

    public static boolean deepRomanChecker(String romanOperand) {

        List<String> operandAsList = Arrays.asList(romanOperand.split(""));
        String romanNumbs = String.join("", CONVERT_MAP.keySet()).substring(4);
        int duplicate = 1;

        for (int i = 0; i < operandAsList.size() - 1; i++) {
            if ((romanNumbs.contains(operandAsList.get(i)) && romanNumbs.contains(operandAsList.get(i + 1))) || duplicate > 3) {
                return false;
            } else if (operandAsList.get(i).equals(operandAsList.get(i + 1))) {
                duplicate += 1;
            }
        }
        return true;
    }

    public static String ConvertingToArabNum(String operand) {

        List<String> operandAsList = new ArrayList<>(List.of(operand.split("")));
        int answer = CONVERT_MAP.get(operandAsList.get(operandAsList.size() - 1));

        for (int i = operandAsList.size() - 1; i > 0; i--) {
            answer += (CONVERT_MAP.get(operandAsList.get(i)) <= CONVERT_MAP.get(operandAsList.get(i - 1)))
                    ? CONVERT_MAP.get(operandAsList.get(i - 1))
                    : -CONVERT_MAP.get(operandAsList.get(i - 1));
        }
        return String.valueOf(answer);
    }
}

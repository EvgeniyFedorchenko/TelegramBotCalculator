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
            throw new UncorrectedRomanOperand("Uncorrected roman operand: " + operand, operand);
        } else if (isRoman) {
            GeneralLogic.romanOperandsArePresent = true;
            return true;
        }
        return false;
    }

    private static boolean deepRomanChecker(String romanOperand) {

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

    public static String convertingToArabNum(String operand) {

        List<String> operandAsList = new ArrayList<>(List.of(operand.split("")));
        int answer = CONVERT_MAP.get(operandAsList.get(operandAsList.size() - 1));

        for (int i = operandAsList.size() - 1; i > 0; i--) {
            answer += (CONVERT_MAP.get(operandAsList.get(i)) <= CONVERT_MAP.get(operandAsList.get(i - 1)))
                    ? CONVERT_MAP.get(operandAsList.get(i - 1))
                    : -CONVERT_MAP.get(operandAsList.get(i - 1));
        }
        return String.valueOf(answer);
    }

    public static String convertingToRomanNum(int operand) {

        if (operand == 0) return "N";

        boolean arabNumberIsNegative = false;
        if (operand < 0) {
            operand = Math.abs(operand);
            arabNumberIsNegative = true;
        }
        StringBuilder romNumber = new StringBuilder();
        int stepOfConvertMap = 1000;
        Map<Integer, String> reverseConvertMap = new HashMap<>(13) {{
                              put(1, "I");
            put(4, "IV");     put(5, "V");
            put(9, "IX");     put(10, "X");
            put(40, "XL");    put(50, "L");
            put(90, "XC");    put(100, "C");
            put(400, "CD");   put(500, "D");
            put(900, "CM");   put(1000, "M");
        }};

        while (operand > 4) {
            while (operand >= stepOfConvertMap) {   // 1000 - 100 - 10
                romNumber.append(reverseConvertMap.get(stepOfConvertMap));
                operand -= stepOfConvertMap;
            }
            stepOfConvertMap -= stepOfConvertMap / 10;

            if (operand  >= stepOfConvertMap) {   // 900 - 90 - 9
                romNumber.append(reverseConvertMap.get(stepOfConvertMap));
                operand -= stepOfConvertMap;
            }
            stepOfConvertMap = (stepOfConvertMap / 9) * 5;

            if (operand  >= stepOfConvertMap) {   // 500 - 50 - 5
                romNumber.append(reverseConvertMap.get(stepOfConvertMap));
                operand -= stepOfConvertMap;
            }
            stepOfConvertMap -= stepOfConvertMap / 5;

            if (operand  >= stepOfConvertMap) {   // 400 - 40 - 4
                romNumber.append(reverseConvertMap.get(stepOfConvertMap));
                operand -= stepOfConvertMap;
            }
            stepOfConvertMap /= 4;   // 1
        }

        while (operand > 0) {
            romNumber.append(reverseConvertMap.get(1));
            operand -= 1;
            stepOfConvertMap -= 1;
        }

        return arabNumberIsNegative ? ("-" + romNumber) : String.valueOf((romNumber));
    }
}

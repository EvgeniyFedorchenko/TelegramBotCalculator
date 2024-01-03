package com.evgeniyfedorchenko.telegrambotcalculator.logic;

import java.util.Arrays;
import java.util.List;

public class RomanNumbersUtils {
    public static boolean checkRoman(String operand) {

        List<String> romanNumbers = Arrays.asList("I", "V", "X", "L", "C", "D", "M", "N");
        return romanNumbers.contains(operand.split("")[0]);
    }

    public static boolean checkCorrectRoman(String romanOperand) {

        List<String> operandAsList = Arrays.asList(romanOperand.split(""));

        String romNumMultiple10 = "I X C M";
        String romNumMultiple5 = "V L D";
        int duplicate = 1;

        for (int i = 0; i < operandAsList.size() - 1; i++) {
            if (operandAsList.get(i).equals(operandAsList.get(i + 1))) {
                duplicate += 1;
                if (duplicate > 3 && romNumMultiple10.contains(operandAsList.get(i))) {
                    return false;
                }
                if (duplicate > 1 && romNumMultiple5.contains(operandAsList.get(i))) {
                    return false;
                }
            } else duplicate = 1;
        }
        return true;
    }



    public static String ConvertingToArabNum(String operand) {

        String[] romOperandArray = operand.split("");
        int[] arabOperandArray = new int[romOperandArray.length];

        for (int i = 0; i < romOperandArray.length; i++) {
            switch (romOperandArray[i]) {
                case "I" -> arabOperandArray[i] = 1;
                case "V" -> arabOperandArray[i] = 5;
                case "X" -> arabOperandArray[i] = 10;
                case "L" -> arabOperandArray[i] = 50;
                case "C" -> arabOperandArray[i] = 100;
                case "D" -> arabOperandArray[i] = 500;
                case "M" -> arabOperandArray[i] = 1000;
                case "N" -> arabOperandArray[i] = 0;
            }
        }
        int arabNum = arabOperandArray[arabOperandArray.length - 1];
        for (int i = arabOperandArray.length - 1; i > 0; i--) {
            if (arabOperandArray[i] <= arabOperandArray[i - 1]) {
                arabNum += arabOperandArray[i - 1];
            } else arabNum -= arabOperandArray[i - 1];
        }
        return Integer.toString(arabNum);
    }

}

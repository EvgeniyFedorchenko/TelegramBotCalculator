package com.evgeniyfedorchenko.telegrambotcalculator.logic;

import com.evgeniyfedorchenko.telegrambotcalculator.exceptions.UncorrectedRomanOperand;

public class Convert {

    public static String ConvertingToArabNum(String operand) {

        String[] romOperandArray = operand.split("");
        int[] arabOperandArray = new int[romOperandArray.length];


        // Защита от ошибочной записи операндов по типу IIII и VV
        String romMultiple10 = "I X C M", romNumMultiple5 = "V L D";
        int duplicate = 1;
        for (int i = 0; i < romOperandArray.length - 1; i++) {
            if (romOperandArray[i].equals(romOperandArray[i + 1])) {
                duplicate += 1;
                if (duplicate > 3 && romMultiple10.contains(romOperandArray[i]) ||
                        duplicate > 1 && romNumMultiple5.contains(romOperandArray[i])) {
                    // TODO Возврат этой ошибки передается корректно
                    return "Is uncorrected: " + operand;
//                    throw new UncorrectedRomanOperand("Roman operand " + operand + " is uncorrected", operand);
                }
            } else duplicate = 1;
        }

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
                // TODO Проверить, как этот и все остальные тексты об ошибках пробрасываются через методы до Bot.java
                default -> { return "Ошибка в выражении, проверте корректность операндов"; }
            }
        }
        int arabNum = arabOperandArray[arabOperandArray.length - 1];
        for (int i = arabOperandArray.length - 1; i > 0; i--) {
            if (arabOperandArray[i] <= arabOperandArray[i - 1]) {
                arabNum += arabOperandArray[i - 1];
            } else arabNum -= arabOperandArray[i - 1];
        }
        return Integer.toString(arabNum); // Возвращаем String, чтобы, если что, можно было вернуть текст об ошибке
    }

}

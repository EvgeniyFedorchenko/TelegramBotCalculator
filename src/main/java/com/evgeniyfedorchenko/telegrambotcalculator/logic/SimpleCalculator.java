package com.evgeniyfedorchenko.telegrambotcalculator.logic;

import com.evgeniyfedorchenko.telegrambotcalculator.exceptions.MathException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class SimpleCalculator {

    public String calculateExpression(List<String> expression) {
        if (expression.get(0).equals("(")) {
            expression.remove(0);
            expression.remove(expression.size() - 1);
        }

        for (int i = 0; i < expression.size(); i++) {
            if (expression.get(i).equals("^") || expression.get(i).equals("√")) {
                changeExpressionWithResult(expression, i);
            }
        }
        for (int i = 0; i < expression.size(); i++) {
            if (expression.get(i).equals("!")) {
                changeExpressionWithResult(expression, i);
            }
        }
        for (int i = 0; i < expression.size(); i++) {
            if (expression.get(i).equals("*") || expression.get(i).equals("/")) {
                changeExpressionWithResult(expression, i);
            }
        }
        for (int i = 0; i < expression.size(); i++) {
            if (expression.get(i).equals("+") || expression.get(i).equals("-")) {
                changeExpressionWithResult(expression, i);
            }
        }
        return expression.get(0);
    }

    private void changeExpressionWithResult(List<String> expression, int i) {

        List<String> simplestExpression = new ArrayList<>(expression.subList(i - 1, i + 2));

        expression.removeAll(simplestExpression);
        expression.add(i - 1, calculating(simplestExpression));
    }


    private String calculating(List<String> simplestExpression) {

        double firstOperand = Double.parseDouble(simplestExpression.get(0));
        double secondOperand = Double.parseDouble(simplestExpression.get(2));

        if (secondOperand == 0.0D) {
            throw new MathException("Деление на ноль");
        } else if (firstOperand == (int) firstOperand && simplestExpression.get(1).equals("!")) {
            throw new MathException("Факториал дробного числа");
        }

        double expression = switch (simplestExpression.get(1)) {
            case "√" -> Math.pow(secondOperand, 1 / firstOperand);
            case "^" -> Math.pow(firstOperand, secondOperand);
            case "!" -> IntStream.rangeClosed(2, (int) Math.round(firstOperand)).reduce((x, y) -> x * y).getAsInt();
            case "*" -> firstOperand * secondOperand;
            case "/" -> firstOperand / secondOperand;
            case "+" -> firstOperand + secondOperand;
            default -> firstOperand - secondOperand;
        };
        return String.valueOf(expression);
    }
}

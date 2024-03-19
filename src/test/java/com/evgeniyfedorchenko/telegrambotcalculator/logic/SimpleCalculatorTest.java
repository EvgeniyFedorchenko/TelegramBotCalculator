package com.evgeniyfedorchenko.telegrambotcalculator.logic;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class SimpleCalculatorTest {

    @Test
    void calculateExpression() {

        SimpleCalculator out = new SimpleCalculator();
        String strExp = "3+4*5";
        List<String> list = new ArrayList<>(Arrays.asList(strExp.split("")));
        String actual = out.calculateExpression(list);
        assertThat(actual).isEqualTo("23.0");
    }

}
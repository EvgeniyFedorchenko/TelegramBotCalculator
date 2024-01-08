package com.evgeniyfedorchenko1.telegrambotcalculator;

import static com.evgeniyfedorchenko1.telegrambotcalculator.logic.RomanNumeralsUtils.deepRomanChecker;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TelegramBotCalculatorTest {

    @org.junit.jupiter.api.Test
    void romanCheckerTest() {
        boolean actual = deepRomanChecker("XLIX");
        boolean expected = true;
        assertEquals(expected, actual);
    }

}

package com.evgeniyfedorchenko.telegrambotcalculator.logic;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GeneralLogicTest {

    @Test
    void mainCalc() {
        GeneralLogic generalLogic = new GeneralLogic();
        assertThat(generalLogic.mainCalc("3,4+(3+1)*(5+2)")).isEqualTo("31.4");
    }
}
package com.evgeniyfedorchenko.telegrambotcalculator.exceptions;

import java.io.IOException;

public class UncorrectedRomanOperand extends IOException {

    String romanOperand;
    public UncorrectedRomanOperand(String message, String romanOperand) {
        super(message);
        this.romanOperand = romanOperand;
    }

    public String getRomanOperand() {
        return romanOperand;
    }
}

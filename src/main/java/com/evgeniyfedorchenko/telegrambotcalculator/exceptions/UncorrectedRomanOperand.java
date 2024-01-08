package com.evgeniyfedorchenko.telegrambotcalculator.exceptions;

public class UncorrectedRomanOperand extends RuntimeException {

    String romanOperand;

    public UncorrectedRomanOperand(String message, String romanOperand) {
        super(message);
        this.romanOperand = romanOperand;
    }

    public String getRomanOperand() {
        return romanOperand;
    }
}

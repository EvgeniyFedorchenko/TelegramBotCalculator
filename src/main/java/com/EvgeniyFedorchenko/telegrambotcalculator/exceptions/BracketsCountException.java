package com.evgeniyfedorchenko.telegrambotcalculator.exceptions;

import java.io.IOException;

public class BracketsCountException extends IOException {
    public BracketsCountException(String message) {
        super(message);
    }
}

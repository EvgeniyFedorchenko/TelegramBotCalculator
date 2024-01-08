package com.evgeniyfedorchenko1.telegrambotcalculator.exceptions;

import java.io.IOException;

public class BracketsCountException extends IOException {
    public BracketsCountException(String message) {
        super(message);
    }
}

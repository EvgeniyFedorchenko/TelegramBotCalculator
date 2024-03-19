package com.evgeniyfedorchenko.telegrambotcalculator.exceptions;

import java.io.IOException;

public class BracketsCountException extends RuntimeException {
    public BracketsCountException(String message) {
        super(message);
    }
}

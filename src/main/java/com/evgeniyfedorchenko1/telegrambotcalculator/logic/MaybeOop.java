package com.evgeniyfedorchenko1.telegrambotcalculator.logic;

import java.util.ArrayList;
import java.util.List;

public class MaybeOop {
    private final List<String> listOfInput;

    public MaybeOop(String expression1) {

        this.listOfInput = new ArrayList<>(List.of(expression1.toUpperCase()
                .replace(" ", "")
                .replace("÷", "/")
                .replace(":", "/")
                .split("(?<=[()!^√*/+-])|(?=[()!^√*/+-])")));
    }

    public List<String> getListOfInput() {
        return listOfInput;
    }

    public void setListValue(int index, String value) {
        this.getListOfInput().set(index, value);
    }

    public int getIndexOfListElement(String element) {
        return this.getListOfInput().indexOf(element);
    }
}

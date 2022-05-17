package com.zappts.MagicTheGathering.exception;

public class SomeCardsNotFoundException extends Exception {
    public SomeCardsNotFoundException() {
        super("Algumas cartas não foram encontradas com isso a operação não foi executada.");
    }
}

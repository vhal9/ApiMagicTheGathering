package com.zappts.MagicTheGathering.exception;

public class SomeCardsNotFoundException extends Exception {
    public SomeCardsNotFoundException() {
        super("Algumas cartas não existem ou não são suas, com isso a operação não foi executada.");
    }
}

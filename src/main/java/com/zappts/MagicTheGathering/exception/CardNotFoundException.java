package com.zappts.MagicTheGathering.exception;

public class CardNotFoundException extends Exception {
    public CardNotFoundException(Long id) {
        super(String.format("carta com id %s não encontrada.", id));
    }
}

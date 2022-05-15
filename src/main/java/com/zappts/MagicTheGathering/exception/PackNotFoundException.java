package com.zappts.MagicTheGathering.exception;

public class PackNotFoundException extends Exception {
    public PackNotFoundException(Long id) {
        super(String.format("Pack com o id %s não encontrado", id));
    }
}

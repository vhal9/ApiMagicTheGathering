package com.zappts.MagicTheGathering.exception;

public class PackAlreadyExistsException extends Exception {
    public PackAlreadyExistsException(Long id) {
        super(String.format("Pack com id %s já existe", id));
    }
}

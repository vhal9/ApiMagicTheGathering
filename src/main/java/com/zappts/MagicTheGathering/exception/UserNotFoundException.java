package com.zappts.MagicTheGathering.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(Long id) {
        super(String.format("usuário com id %s não encontrado.", id));
    }
}

package com.zappts.MagicTheGathering.exception;

public class UsernameAlreadyExistsException extends Exception {
    public UsernameAlreadyExistsException() {
        super("Username já existe na base de dados, escolha um novo e tente novamente");
    }
}

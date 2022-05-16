package com.zappts.MagicTheGathering.exception;

public class ForbiddenException extends Exception {
    public ForbiddenException() {
        super("Usuário não tem permissão ao recurso.");
    }
}

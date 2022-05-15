package com.zappts.MagicTheGathering.exception;

public class RemoveNonExistentCardException extends Exception {
    public RemoveNonExistentCardException() {
        super("Não é possível remover carta inexistente");
    }

}

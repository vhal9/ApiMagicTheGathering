package com.zappts.MagicTheGathering.controller.advice;

import com.zappts.MagicTheGathering.exception.*;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return handleNotFoundException(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> erros = ex.getBindingResult().getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return new ResponseEntity<>(erros, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<String> handleCardNotFoundException(CardNotFoundException ex) {
        return handleNotFoundException(ex.getMessage());
    }

    @ExceptionHandler(PackNotFoundException.class)
    public ResponseEntity<String> handlePackNotFoundException(PackNotFoundException ex) {
        return handleNotFoundException(ex.getMessage());
    }

    @ExceptionHandler(RemoveNonExistentCardException.class)
    public ResponseEntity<String> handleRemoveNonExistentCardException(RemoveNonExistentCardException ex) {
        return handleNotFoundException(ex.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<String> handleForbiddenException(ForbiddenException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(PackAlreadyExistsException.class)
    public ResponseEntity<String> handlePackAlreadyExistsException(PackAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<String> handleNotFoundException(String message) {
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

}

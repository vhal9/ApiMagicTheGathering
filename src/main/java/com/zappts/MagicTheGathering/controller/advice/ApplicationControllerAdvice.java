package com.zappts.MagicTheGathering.controller.advice;

import com.zappts.MagicTheGathering.exception.CardNotFoundException;
import com.zappts.MagicTheGathering.exception.PackNotFoundException;
import com.zappts.MagicTheGathering.exception.UserNotFoundException;
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

    private ResponseEntity<String> handleNotFoundException(String message) {
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

}

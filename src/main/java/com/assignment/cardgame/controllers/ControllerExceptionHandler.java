package com.assignment.cardgame.controllers;

import com.assignment.cardgame.services.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.xml.bind.ValidationException;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    ErrorMessage gameNotFoundExceptionHandler(EntityNotFoundException e){
        return new ErrorMessage("404", e.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    ErrorMessage validationExceptionHandler(ValidationException e){
        return new ErrorMessage("400", e.getMessage());
    }
}

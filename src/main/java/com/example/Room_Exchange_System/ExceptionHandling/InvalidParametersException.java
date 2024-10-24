package com.example.Room_Exchange_System.ExceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidParametersException extends RuntimeException{
    public InvalidParametersException(String message){
        super(message);
    }
}

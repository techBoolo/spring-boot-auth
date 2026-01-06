package com.example.auth_git.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class RecordExistsException extends RuntimeException{

    public RecordExistsException(String message){
        super(message);
    }

    public RecordExistsException(String message, Throwable cause){
        super(message, cause);
    }
}

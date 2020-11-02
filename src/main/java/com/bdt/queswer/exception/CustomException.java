package com.bdt.queswer.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class CustomException extends Exception{
    HttpStatus status = HttpStatus.BAD_REQUEST;
    public CustomException(String message) {
        super(message);
    }
    public CustomException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}

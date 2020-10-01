package com.bdt.queswer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLException;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> customException(Exception e, WebRequest req) {
        ErrorResponse body = new ErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage());
        return new ResponseEntity<ErrorResponse>(body,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> authenException(Exception e, WebRequest req) {
        ErrorResponse body = new ErrorResponse(HttpStatus.FORBIDDEN,"Sai tên đăng nhập hoặc mật khẩu");
        return new ResponseEntity<ErrorResponse>(body,HttpStatus.FORBIDDEN);
    }
}

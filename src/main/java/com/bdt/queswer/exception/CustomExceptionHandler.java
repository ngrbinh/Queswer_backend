package com.bdt.queswer.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import org.postgresql.util.PSQLException;

@RestControllerAdvice
public class
CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> customException(CustomException e, WebRequest req) {
        ErrorResponse body = new ErrorResponse(e.getStatus(),e.getMessage());
        return new ResponseEntity<ErrorResponse>(body,e.getStatus());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> authenException(Exception e, WebRequest req) {
        ErrorResponse body = new ErrorResponse(HttpStatus.FORBIDDEN,e.toString());
        return new ResponseEntity<ErrorResponse>(body,HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> expiredJwtException(Exception e, WebRequest req) {
        ErrorResponse body = new ErrorResponse(HttpStatus.FORBIDDEN,"Phiên đăng nhập đã hết hạn");
        return new ResponseEntity<ErrorResponse>(body,HttpStatus.FORBIDDEN);
    }
}

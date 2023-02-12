package com.example.movieservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.stream.Collectors;

/**
 * 4:04 PM 17-Jan-23
 * Long Tran
 */
@ControllerAdvice
@Slf4j
public class GlobalHandleException {

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<?> handleException(WebExchangeBindException e){
        log.error("Error: {}", e.getMessage(),e);
        var error =  e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).sorted().collect(Collectors.joining(","));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException e){
        log.error("Error: {}", e.getMessage(),e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }


}

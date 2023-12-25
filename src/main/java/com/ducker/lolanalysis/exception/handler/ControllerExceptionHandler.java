package com.ducker.lolanalysis.exception.handler;

import com.ducker.lolanalysis.dto.response.Response;
import com.ducker.lolanalysis.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response<Void>> handleNotFoundException(NotFoundException e) {
        log.info("Error: ", e);
        return new ResponseEntity<>(Response.with(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
    }
}

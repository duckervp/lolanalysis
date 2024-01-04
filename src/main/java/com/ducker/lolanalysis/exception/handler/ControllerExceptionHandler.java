package com.ducker.lolanalysis.exception.handler;

import com.ducker.lolanalysis.dto.response.Response;
import com.ducker.lolanalysis.exception.NotFoundException;
import com.ducker.lolanalysis.exception.RiotTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {
    private static final String LOG_PATTERN = "Error: ";

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response<Void>> handleNotFoundException(NotFoundException e) {
        log.info(LOG_PATTERN, e);
        return new ResponseEntity<>(Response.with(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RiotTokenException.class)
    public ResponseEntity<Response<Void>> handleRiotTokenException(RiotTokenException e) {
        log.info(LOG_PATTERN, e);
        return new ResponseEntity<>(
                Response.with(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Invalid Riot Games API Key"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Void>> handleException(Exception e) {
        log.info(LOG_PATTERN, e);
        return new ResponseEntity<>(
                Response.with(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

package learn.spotifyPlaylist.controllers;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Object> handleDataException(Result<Object> result) {

        return new ResponseEntity<>(
                ErrorResponse.build(result).getStatusCode());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public  ResponseEntity<Object> handleException(Result<Object> result) {

        return new ResponseEntity<>(
                ErrorResponse.build(result).getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Result<Object> result) {

        return new ResponseEntity<>(
                ErrorResponse.build(result).getStatusCode());
    }



}

